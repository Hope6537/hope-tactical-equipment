package org.hope6537.note.thinking_in_java.eighteen;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 打印文件目录树
 * @signdate 2014年7月23日下午2:28:20
 * @company Changchun University&SHXT
 */
public final class Directory {
    /**
     * @param dir
     * @param regex
     * @return
     * @descirbe 返回当前dir下的第一层目录
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午3:08:39
     * @version 0.9
     */
    public static File[] local(File dir, final String regex) {
        return dir.listFiles(/**
                 * @describe
                 *           生成的是一个内部FilenameFilter的实现类，该类将用于list()方法回调accept方法来进行匹配
                 * @author Hope6537(赵鹏)
                 * @signdate 2014年7月23日下午3:08:53
                 * @version 0.9
                 * @company Changchun University&SHXT
                 */
                new FilenameFilter() {
                    /**
                     * @describe 因为内部类要使用regex 所以必须final
                     */
                    private Pattern pattern = Pattern.compile(regex);

                    @Override
                    public boolean accept(File dir, String name) {
                        return pattern.matcher(new File(name).getName()).matches();
                    }
                });
    }

    /**
     * @param path
     * @param regex
     * @return
     * @descirbe local方法的驱动
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午3:09:57
     * @version 0.9
     */
    public static File[] local(String path, final String regex) {
        return local(new File(path), regex);
    }

    /**
     * @param startDir
     * @param regex
     * @return
     * @descirbe 递归遍历出当前startDir下的所有File文件，并最终返回生成目录树
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午3:12:05
     * @version 0.9
     */
    static TreeInfo recurseDirs(File startDir, String regex) {
        TreeInfo result = new TreeInfo();
        // 对startDir出的list出来的目录进行遍历
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                // 如果是目录，那么添加进目录List
                result.dirs.add(item);
                // 同时接着将该目录开始的遍历之后的TreeInfo再合并
                result.addAll(recurseDirs(item, regex));
            } else {
                // 如果是文件，直接添加进文件List(前提，必须符合正则表达式)
                if (item.getName().matches(regex)) {
                    result.files.add(item);
                }
            }
        }
        // 最终返回目录树
        return result;
    }

    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex) {
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(File start) {
        return recurseDirs(start, ".*");
    }

    public static TreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    /**
     * @param args
     * @descirbe 整个遍历的驱动例程
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午3:15:59
     * @version 0.9
     */
    public static void start(String[] args) {

        if (args.length == 0) {
            System.out.println(walk("."));
        } else {
            for (String arg : args) {
                System.out.println(walk(arg));
            }
        }

    }

    public static void main(String[] args) {
        start(new String[]{"D:\\Materials"});
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 一个目录树的实体
     * @signdate 2014年7月23日下午3:10:39
     * @company Changchun University&SHXT
     */
    public static class TreeInfo implements Iterable<File> {
        /**
         * @describe 该List保存文件
         */
        public List<File> files = new ArrayList<File>();
        /**
         * @describe 该List保存目录
         */
        public List<File> dirs = new ArrayList<File>();

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Iterable#iterator() 生成关于目录文件的迭代器
         *
         * @author:Hope6537(赵鹏)
         */
        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        /**
         * @param other
         * @descirbe 如果有另一个目录树，添加的例程
         * @author Hope6537(赵鹏)
         * @signDate 2014年7月23日下午3:11:33
         * @version 0.9
         */
        void addAll(TreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString() 格式化输出
         *
         * @author:Hope6537(赵鹏)
         */
        @Override
        public String toString() {
            return "dirs: " + PPrint.pformat(dirs) + "\n\nfiles: "
                    + PPrint.pformat(files);
        }

    }
}
