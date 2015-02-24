package org.hope6537.email;

import org.hope6537.email.driver.EmailSender;
import org.hope6537.email.driver.SimpleMailSender;
import org.hope6537.email.model.MailSenderInfo;
import org.junit.Test;

/**
 * Created by Hope6537 on 2015/2/24.
 */
public class EmailSenderTest {

    @Test
    public void testSend1() {
        EmailSender email = new EmailSender();
        email.setSendName("jichuang");
        email.setFromAddress("**@qq.com");
        email.setUsername("**@qq.com");
        email.setPassword("***");
        email.setSubject("Thanks for giving advice");
        email.setContent("测试邮件");
        email.setSmtpHost("smtp.qq.com");
        email.setSmtpPort("465");

        email.setToAddress("hope6537@qq.com");

        boolean result = email.sendHtmlMail();
        System.out.println(result);
    }

    @Test
    public void testSend2() {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("**@163.com");
        mailInfo.setPassword("***");//您的邮箱密码
        mailInfo.setFromAddress("**@163.com");
        mailInfo.setToAddress("hope6537@qq.com");
        mailInfo.setSubject("JiChuangTest");
        mailInfo.setContent("JiChuangTest");
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        //sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo);//发送html格式
    }
}
