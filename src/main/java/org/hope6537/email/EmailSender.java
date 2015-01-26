package org.hope6537.email;

import org.apache.commons.lang.StringUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailSender {

    private String smtpHost;
    private String smtpPort;
    //发件人姓名
    private String sendName;
    // 邮件发送者的地址
    private String fromAddress;
    // 邮件接收者的地址，多个地址用分号分隔，不能以分号结尾
    private String toAddress;
    // 登陆邮件发送服务器的用户名
    private String username;
    // 登陆邮件发送服务器的密码
    private String password;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;

    /**
     * 获得邮件会话属性
     */
    private Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", getSmtpHost());
        p.put("mail.smtp.port", getSmtpPort());
        p.put("mail.smtp.auth", true);
        return p;
    }

    /**
     * 以HTML格式发送邮件
     *
     */
    public boolean sendHtmlMail() {
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session mailSession = Session.getDefaultInstance(getProperties(), new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUsername(), getPassword());
            }
        });
        try {
            // 根据session创建一个邮件消息
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(getNickAndFromAddress()));
            message.setRecipients(Message.RecipientType.TO, getToAddresses());
            message.setSubject(getSubject());
            message.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            message.setContent(mainPart);
            // 发送邮件
            Transport.send(message);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String getNickAndFromAddress() {
        String nick = null;
        try {
            nick = MimeUtility.encodeText(getSendName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String from = getFromAddress();
        if(StringUtils.isNotBlank(nick)){
            from = nick + " <" + from + ">";
        }
        return from;
    }

    private InternetAddress[] getToAddresses() throws AddressException {
        String[] addressArray = getToAddress().split(";");
        InternetAddress[] address = new InternetAddress[addressArray.length];
        for (int i = 0; i < addressArray.length; i++) {
            address[i] = new InternetAddress(addressArray[i]);
        }
        return address;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
