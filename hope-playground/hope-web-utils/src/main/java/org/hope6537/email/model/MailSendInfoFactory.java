package org.hope6537.email.model;

import java.lang.reflect.Constructor;

/**
 * Created by Hope6537 on 2015/3/1.
 */
public class MailSendInfoFactory {

    private static MailSenderInfo mailSenderInfo;

    static {
        try {
            Class clz = Class.forName(MailSenderInfo.class.getName());
            Constructor constructor = clz.getConstructor();
            constructor.setAccessible(true);
            mailSenderInfo = (MailSenderInfo) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MailSenderInfo getInstance(String username, String password) {
        mailSenderInfo.setMailServerHost("smtp.163.com");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName(username);
        mailSenderInfo.setPassword(password);
        mailSenderInfo.setFromAddress(username);
        return mailSenderInfo;
    }

}
