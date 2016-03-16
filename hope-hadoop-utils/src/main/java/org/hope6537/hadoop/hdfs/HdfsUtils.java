package org.hope6537.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapred.JobConf;
import org.hope6537.hadoop.ConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by Hope6537 on 2015/2/13.
 */
public class HdfsUtils {

    public static final String HADOOP2MASTER_DIR = "hdfs://hadoop2master:9000";
    public static final String HADOOP_NAMESERVICE_DIR = "hdfs://ns1";
    public static final String JICHUANG_MASTER_DIR = "hdfs://jichuang:9000";
    public static final String HOPE_MASTER_DIR = "hdfs://www.hope6537.com:9000";
    public Logger logger;
    private FileSystem fileSystem;
    private String username;

    private String hdfsDir;
    private Configuration configuration;

    public HdfsUtils(String hdfsDir, Configuration configuration, String username) {
        this.hdfsDir = hdfsDir;
        this.configuration = configuration;
        this.username = username;
        this.logger = LoggerFactory.getLogger(getClass());
        try {
            this.fileSystem = FileSystem.get(URI.create(hdfsDir), configuration, username);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public HdfsUtils(Configuration configuration) {

        this(HADOOP_NAMESERVICE_DIR, configuration);
    }

    public HdfsUtils() {

        this(HADOOP_NAMESERVICE_DIR, ConfigurationFactory.getConfiguration());
    }

    public HdfsUtils(String hdfsDir, Configuration configuration) {
        this(hdfsDir, configuration, "hope6537");
    }

    public static HdfsUtils getInstanceOfDistributed() {
        return new HdfsUtils(HADOOP_NAMESERVICE_DIR, ConfigurationFactory.getConfiguration());
    }

    public static HdfsUtils getInstanceOfPseudoDistributed(Configuration configuration) {
        return new HdfsUtils(HADOOP2MASTER_DIR, configuration);
    }

    public static HdfsUtils getInstanceOfJiChuang(Configuration configuration) {
        return new HdfsUtils(JICHUANG_MASTER_DIR, configuration);
    }

    public static HdfsUtils getInstanceOfHope(Configuration configuration) {
        return new HdfsUtils(HOPE_MASTER_DIR, configuration);
    }


    public static JobConf config() {
        //设置Configuration配置,获取classpath下的配置文件
        JobConf conf = new JobConf(HdfsUtils.class);
        conf.setJobName("HdfsUtils");
        conf.addResource("classpath:/hadoop/core-site.xml");
        conf.addResource("classpath:/hadoop/hdfs-site.xml");
        conf.addResource("classpath:/hadoop/mapred-site.xml");
        return conf;
    }

    public FileSystem getFileSystem() {
        try {
            fileSystem.getStatus();
        } catch (IOException e) {
            logger.warn("restart fileSystem");
            try {
                this.fileSystem = FileSystem.get(URI.create(hdfsDir), configuration, username);
            } catch (Exception e1) {
                logger.error("create fileSystem error");
            }
        }
        return fileSystem;
    }

    /**
     * 获取文件信息对象，收到后只需要迭代取数据即可
     */
    public FileStatus[] ls(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = getFileSystem();
        FileStatus[] list = fileSystem.listStatus(path);
        return list;
    }

    public boolean mkdirs(String folder) {
        Path path = new Path(folder);
        try {
            if (!fileSystem.exists(path)) {
                fileSystem.mkdirs(path);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("make directory error");
            return false;
        }
    }

    public boolean rmr(String folder) {
        Path path = new Path(folder);
        try {
            fileSystem.deleteOnExit(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("delete file error");
            return false;
        }
    }

    public boolean put(String local, String remote) {
        try {
            fileSystem.copyFromLocalFile(new Path(local), new Path(remote));
            logger.info("upload file " + local + " to " + remote);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("put file error");
            return false;
        }
    }

    public boolean put(InputStream local, String remote) {
        try {
            OutputStream out = fileSystem.create(new Path(remote), false);
            IOUtils.copyBytes(local, out, configuration, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("put file error");
            return false;
        }
    }

    public OutputStream getHdfsOutPutStream(String path) {
        try {
            OutputStream out = fileSystem.create(new Path(path), false);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public OutputStream getHdfsOutPutStreamOfPseudoDistributed(String path) {
        try {
            Path f = new Path(path);
            OutputStream out = f.getFileSystem(this.configuration).create(f, false);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getHdfsInputStream(String path) {
        try {
            InputStream in = fileSystem.open(new Path(path));
            return in;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InputStream getHdfsInputStreamOfPseudoDistributed(String path) {
        try {
            Path f = new Path(path);
            InputStream in = f.getFileSystem(this.configuration).open(f);
            return in;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean get(String remote, String local) throws IOException {
        try {
            fileSystem.copyToLocalFile(new Path(remote), new Path(local));
            logger.info("download file " + remote + " from " + local);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get file error");
            return false;
        }
    }

    public InputStream cat(String remoteFile) throws IOException {
        Path path = new Path(remoteFile);
        FSDataInputStream fsdis = null;
        logger.info("cat: " + remoteFile);
        try {
            fsdis = fileSystem.open(path);
            return fsdis;
        } finally {
            IOUtils.closeStream(fsdis);
        }
    }

    public void cat(String remoteFile, OutputStream out) throws IOException {
        InputStream fsdis = null;
        try {
            fsdis = cat(remoteFile);
            IOUtils.copyBytes(fsdis, out, 4096, false);
        } finally {
            IOUtils.closeStream(fsdis);
        }
    }

    public void catInConsole(String remoteFile) throws IOException {
        this.cat(remoteFile, System.out);

    }

    public void rmrShowInConsole(String folder) {
        boolean result = this.rmr(folder);
        if (result) {
            System.out.println("Delete: " + folder + " Success!");
        } else {
            System.out.println("Delete: " + folder + "Failed!");
        }
    }


    public void mkdirsShowInConsole(String folder) {
        boolean result = this.mkdirs(folder);
        if (result) {
            System.out.println("Create: " + folder + " Success!");
        } else {
            System.out.println("Create: " + folder + "Failed!");
        }
    }

    private static Path checkDest(String srcName, FileSystem dstFS, Path dst, boolean overwrite) throws IOException {
        if (dstFS.exists(dst)) {
            FileStatus sdst = dstFS.getFileStatus(dst);
            if (sdst.isDirectory()) {
                if (null == srcName) {
                    throw new IOException("Target " + dst + " is a directory");
                }
                return checkDest(null, dstFS, new Path(dst, srcName), overwrite);
            } else if (!overwrite) {
                throw new IOException("Target " + dst + " already exists");
            }
        }
        return dst;
    }

    private static void checkDependencies(FileSystem srcFS, Path src, FileSystem dstFS, Path dst)
            throws IOException {
        if (srcFS == dstFS) {
            String srcq = src.makeQualified(srcFS).toString() + Path.SEPARATOR;
            String dstq = dst.makeQualified(dstFS).toString() + Path.SEPARATOR;
            if (dstq.startsWith(srcq)) {
                if (srcq.length() == dstq.length()) {
                    throw new IOException("Cannot copy " + src + " to itself.");
                } else {
                    throw new IOException("Cannot copy " + src + " to its subdirectory " +
                            dst);
                }
            }
        }
    }

    /**
     * Copy files between FileSystems.
     */
    public static boolean copyBetweenFileSystem(FileSystem srcFS, FileStatus srcStatus,
                                                FileSystem dstFS, Path dst,
                                                boolean deleteSource,
                                                boolean overwrite,
                                                Configuration conf) throws IOException {
        //得到源地址
        Path src = srcStatus.getPath();
        //得到是否合法
        dst = checkDest(src.getName(), dstFS, dst, overwrite);
        //如果目标文件是一个目录
        if (srcStatus.isDirectory()) {
            //检查复制合法性,合法就创建目录
            checkDependencies(srcFS, src, dstFS, dst);
            if (!dstFS.mkdirs(dst)) {
                return false;
            }
            FileStatus contents[] = srcFS.listStatus(src);
            //获得当前目录的子文件
            for (int i = 0; i < contents.length; i++) {
                //然后递归调用本方法
                copyBetweenFileSystem(srcFS, contents[i], dstFS,
                        new Path(dst, contents[i].getPath().getName()),
                        deleteSource, overwrite, conf);
            }
        } else {
            //如果目标是文件
            InputStream in = null;
            OutputStream out = null;
            try {
                //输入流
                in = srcFS.open(src);
                //输出流，同时是否覆盖
                out = dstFS.create(dst, overwrite);
                //然后获取输入，输出，配置文件,写入
                IOUtils.copyBytes(in, out, conf, true);
            } catch (IOException e) {
                IOUtils.closeStream(out);
                IOUtils.closeStream(in);
                throw e;
            }
        }
        //如果要删除源文件
        if (deleteSource) {
            return srcFS.delete(src, true);
        } else {
            return true;
        }

    }


    /**
     * 在控制台显示ls效果
     */
    public void lsShowInConsole(String folder) throws IOException {
        FileStatus[] list = this.ls(folder);
        System.out.println("the path of: " + folder);
        System.out.println("==========================================================");
        for (FileStatus f : list) {
            System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath(), f.isDirectory(), f.getLen());
        }
        System.out.println("==========================================================");
    }

    public void closeFileSystem() {
        try {
            this.fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
