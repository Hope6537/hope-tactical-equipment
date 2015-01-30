package org.hope6537.hadoop.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class WSReducer extends Reducer<Text,LongWritable,Text,LongWritable> {

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

        long counter = 0;
        for(LongWritable longWritable :values){
            counter += longWritable.get();
        }
        context.write(key,new LongWritable(counter));

    }
}
