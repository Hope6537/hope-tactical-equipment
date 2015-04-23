package org.hope6537.note.design.flyweight.exam;

/**
 * 享元模式场景类
 */
public class Client {

    public static void main(String[] args) {

        for (int i = 0; i < 4; i++) {
            String subject = "\"科目\":" + i;
            for (int j = 0; j < 30; j++) {
                String key = subject + ",\"考试地点\":" + j;
                SignInfoFactory.getSignInfo(key);
            }
        }
        SignInfo signInfo = SignInfoFactory.getSignInfo("\"科目\":1,\"考试地点\":1");
        System.out.println(signInfo.toString());
    }

}
