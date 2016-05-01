package org.hope6537.note.tij.eighteen;

import java.io.File;
import java.io.IOException;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 穿透目录进行文件处理
 * @signdate 2014年7月23日下午3:26:40
 * @company Changchun University&SHXT
 */
public class ProcessFiles {

    /**
     * @describe 一个是实体对象
     */
    private Strategy strategy;
    /**
     * @describe 一个是文件后缀名
     */
    private String ext;

    public ProcessFiles(Strategy strategy, String ext) {
        super();
        this.strategy = strategy;
        this.ext = ext;
    }

    public static void main(String[] args) {
        new ProcessFiles(new Strategy() {
            @Override
            public void process(File file) {
                System.out.println(file);
            }
        }, "java").start(args);
    }

    /**
     * @param args
     * @descirbe 工作开始区域
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午3:27:47
     * @version 0.9
     */
    public void start(String[] args) {
        try {
            //如果参数为空的话，那么将从该点开始遍历
            if (args.length == 0) {
                processDirectoryTree(new File("."));
            } else {
                for (String arg : args) {
                    //如果有多重开始点,依次解析并寻找
                    File fileArg = new File(arg);
                    if (fileArg.isDirectory()) {
                        //如果为目录，接着继续
                        processDirectoryTree(fileArg);
                    } else {
                        //如果为文件，处理，如果不符合格式，添加后缀后处理
                        if (!arg.endsWith("." + ext)) {
                            arg += "." + ext;
                        }
                        strategy.process(new File(arg).getCanonicalFile());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param root
     * @throws IOException
     * @descirbe 遍历目录树
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午3:28:36
     * @version 0.9
     */
    public void processDirectoryTree(File root) throws IOException {
        //给出一个根节点root
        //然后获得这个根节点的绝对路径，并以此开始向下遍历 Treeinfo是实现迭代器的 实现的是实体文件的迭代器
        //然后依次给File进行处理
        for (File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext)) {
            //处理的结果是调用对象的process方法
            //在这个实例中process方法仅仅是将其打印出来
            strategy.process(file.getCanonicalFile());

        }
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 定义接口 处理文件的接口
     * @signdate 2014年7月23日下午3:26:51
     * @company Changchun University&SHXT
     */
    public interface Strategy {
        /**
         * @param file
         * @descirbe 处理文件实例
         * @author Hope6537(赵鹏)
         * @signDate 2014年7月23日下午3:27:11
         * @version 0.9
         */
        void process(File file);
    }
}
