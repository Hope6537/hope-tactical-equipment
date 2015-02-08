package org.hope6537.note.design.factory.simple;

import org.hope6537.note.design.factory.human.BlackHuman;
import org.hope6537.note.design.factory.human.Human;

/**
 * <p>Describe: 简单工厂模式</p>
 * <p>缺点是工厂类的拓展比较困难，不符合开闭原则</p>
 * <p>Using: 直接使用HumanFactory.createHuman(BlackHuman.class);调用即可</p>
 * <p>DevelopedTime: 2014年9月5日下午4:36:05</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class HumanFactory {

    public static <T extends Human> T createHuman(Class<T> cls) {

        Human human = null;

        try {
            human = (T) Class.forName(cls.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) human;
    }

    public static void main(String[] args) {
        HumanFactory.createHuman(BlackHuman.class);
    }
}
