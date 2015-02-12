package org.hope6537.hadoop.station;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.context.ApplicationConstant;
import org.hope6537.date.DateFormatCalculate;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基站数据分析驱动类
 */
public class StationDriver {


    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("===================");
            System.err.println("参数个数应该为3个");
            System.err.println("input 数据源路径");
            System.err.println("output 输出数据路径");
            System.err.println("timeLine 采集时间分段");
            System.err.println("例如 /station /station_output 00-09-17-21");
            System.err.println("===================");
        } else {
            System.out.println("任务" + (new StationDriver().run(args) ? "完成" : "失败"));
        }
    }

    public boolean run(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.set("timeLine", args[2]);

        Job job = Job.getInstance(configuration, "StationDriver");
        job.setJarByClass(StationDriver.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(StationMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(StationReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.waitForCompletion(true);

        return job.isSuccessful();

    }


    /**
     * 将数据转化成以
     * [用户信息]->[时段] 为key
     * [基站位置]->[所在时间] 为value的键值对
     */
    public static class StationMapper extends Mapper<LongWritable, Text, Text, Text> {

        private String[] timeLine;
        private StationDataType stationDataType;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            this.timeLine = context.getConfiguration().get("timeLine").split("-");

            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            String fileName = fileSplit.getPath().getName();
            if (fileName.startsWith("pos")) {
                stationDataType = StationDataType.POS;
            } else if (fileName.startsWith("net")) {
                stationDataType = StationDataType.NET;
            } else {
                context.getCounter("Exception", "NoneTypeFile").increment(1);
            }
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StationData stationData = null;
            try {
                stationData = new StationData(line, stationDataType, timeLine);
            } catch (CastException e) {
                if (e.getFlag() == Flag.CASTERROR) {
                    context.getCounter("Exception", "CastError").increment(1);
                }
                if (e.getFlag() == Flag.INDEXOUT) {
                    context.getCounter("Exception", "IndexOut").increment(1);
                }
                if (e.getFlag() == Flag.TYPEERROR) {
                    context.getCounter("Exception", "TypeError").increment(1);
                }
            } catch (Exception e) {
                context.getCounter("Exception", "lineSkip").increment(1);
            }
            if (ApplicationConstant.notNull(stationData)) {
                context.write(stationData.getKey(), stationData.getValue());
            }

        }
    }

    /**
     * 将相同的key的value规约过来，裝在一个迭代器
     * 获取的数据如下[基站位置]->[时间]
     * 然后我們需要將這個迭代器裏面的數據取出
     * 然后按照時間排个序
     */
    public static class StationReducer extends Reducer<Text, Text, NullWritable, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            TreeMap<Long, String> timeAndStation = new TreeMap<>();
            //获得时段终止时间
            String user = key.toString().split("->")[0];
            String timeZone = key.toString().split("->")[1];
            String date = timeZone.substring(0, 11);
            String timeLineEnd = timeZone.substring(timeZone.lastIndexOf('-') + 1);
            //将其放入到有序map里
            for (Text data : values) {
                String[] datas = data.toString().split("->");
                Long dateTime = Long.parseLong(datas[1]);
                String location = datas[0];
                timeAndStation.put(dateTime, location);
            }
            //在最后添加时段終點
            long end = 0;
            try {
                end = DateFormatCalculate.castToCurrentTime(date + timeLineEnd, "yyyy-MM-dd HH");
            } catch (DateTimeParseException e) {
                end = DateFormatCalculate.castToCurrentTime(date + timeLineEnd, "yyyy-MM-dd H");
            }


            timeAndStation.put(end, "end");
            //然后计算之间的时间间隔
            Map<String, Double> map = getStayTime(timeAndStation);


            for (String mapKey : map.keySet()) {
                if (!mapKey.equals("end")) {
                    StringBuilder stringBuilder = new StringBuilder()
                            .append(user)
                            .append("\t")
                            .append(timeZone)
                            .append("\t")
                            .append(mapKey)
                            .append("\t")
                            .append(map.get(mapKey));
                    context.write(NullWritable.get(), new Text(stringBuilder.toString()));
                }

            }

        }


        public Map<String, Double> getStayTime(TreeMap<Long, String> timeAndStation) {
            Map.Entry<Long, String> upload, nextUpload;
            Map<String, Double> resultMap = new HashMap<>();
            Iterator<Map.Entry<Long, String>> entryIterator = timeAndStation.entrySet().iterator();
            upload = entryIterator.next();
            while (entryIterator.hasNext()) {
                nextUpload = entryIterator.next();
                //得出停留时间
                double diff = (nextUpload.getKey() - upload.getKey()) / 60;
                if (diff <= 60.0) {
                    if (resultMap.containsKey(upload.getValue())) {
                        resultMap.put(upload.getValue(), resultMap.get(upload.getValue()) + diff);
                    } else {
                        resultMap.put(upload.getValue(), diff);
                    }
                }
                upload = nextUpload;
            }
            return resultMap;
        }
    }

}
