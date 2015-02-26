package org.hope6537.ssh;

import com.jcraft.jsch.*;
import org.hope6537.context.ApplicationConstant;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Created by Hope6537 on 2015/2/26.
 */
public class SFTPUtil {

    private String userId;
    private String hostname;
    private String password;
    private Session session;
    private JSch jSch;
    private ChannelSftp channel;
    private org.slf4j.Logger logger;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SFTPUtil(String userId, String hostname, String password) {
        logger = LoggerFactory.getLogger(getClass());
        this.userId = userId;
        this.hostname = hostname;
        this.password = password;
        // 建立连接
        logger.info("init connection");
        jSch = new JSch();
        try {
            Session session = jSch.getSession(userId, hostname, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            this.init();
        } catch (JSchException e) {
            logger.error("connection failed");
            e.printStackTrace();
        }
    }

    /**
     * 远程调用sftp
     */
    public void init() throws JSchException {
        if (ApplicationConstant.notNull(session)) {
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
        }
    }

    public Vector ls(String path) throws SftpException {
        if (ApplicationConstant.notNull(channel) && !channel.isClosed()) {
            Vector list = channel.ls(path);
            return list;
        }
        return null;
    }

    public void lsShowConsole(String path) {
        Vector list = null;
        try {
            list = this.ls(path);
        } catch (SftpException e) {
            logger.error("ls failed");
            return;
        }
        if (ApplicationConstant.notNull(list)) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
        logger.info("no item");

    }

    public SftpATTRS lstat(String path) throws SftpException {
        if (ApplicationConstant.notNull(channel) && !channel.isClosed()) {
            SftpATTRS stat = channel.lstat(path);
            return stat;
        }
        return null;
    }

    public void lstatShowConsole(String path) {
        SftpATTRS stat = null;
        try {
            stat = this.lstat(path);
        } catch (SftpException e) {
            logger.error("lstat failed");
            return;
        }
        if (ApplicationConstant.notNull(stat)) {
            System.out.println("---- lstat");
            System.out.println(stat);
            System.out.println(stat.getSize());
        }
        logger.info("no item");

    }


    public void download(String from, String to) {
        if (ApplicationConstant.notNull(channel) && !channel.isClosed()) {
            try {
                channel.get(from, to);
            } catch (SftpException e) {
                logger.error("download failed");
            }
        }
    }

    public void upload(String from, String to) {
        if (ApplicationConstant.notNull(channel) && !channel.isClosed()) {
            try {
                channel.put(new FileInputStream(from), to);
            } catch (SftpException e) {
                logger.error("upload failed");
            } catch (FileNotFoundException e) {
                logger.error("no file exist");
            }
        }
    }

    public void destroy() {
        if (ApplicationConstant.notNull(channel)) {
            channel.disconnect();
        }
        if (ApplicationConstant.notNull(session)) {
            channel.disconnect();
        }
    }

}
