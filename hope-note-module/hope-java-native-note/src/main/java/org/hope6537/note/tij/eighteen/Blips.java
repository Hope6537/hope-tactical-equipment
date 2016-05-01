package org.hope6537.note.tij.eighteen;

import java.io.*;

class Blip1 implements Externalizable {
    /**
     * @describe 注意 所有的[默认]构造器都会被调用
     * @author Hope6537(赵鹏)
     */
    public Blip1() {
        System.out.println("Blip1 Constructor");
    }

    public Blip1(int i) {
        System.out.println("Blip1 Constructor with Param");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip1.writeExternal");
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        System.out.println("Blip1.readExternal");
    }
}

class Blip2 implements Externalizable {
    Blip2() {
        System.out.println("Blip2 Constructor");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip2.writeExternal");
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        System.out.println("Blip2.readExternal");
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 序列化和反序列化实验
 * @signdate 2014年7月25日下午4:06:30
 * @company Changchun University&SHXT
 */
// 最后在写入过程中 没有问题 都是调用了序列化写法 而在还原过程中 1调用了构造器然后再调用序列化进行写入 但是2就出现了异常
// 因为b1的构造器是公共的 而b2的不是
// 和Serializable不同 ，Externalizable会调用构造方法之后 再写入数据
public class Blips {
    public static void main(String[] args) throws IOException,
            ClassNotFoundException {
        System.out.println("Constructing objects:");
        Blip1 b1 = new Blip1();
        Blip2 b2 = new Blip2();
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
                "Blips.out"));
        System.out.println("Saving objects:");
        o.writeObject(b1);
        o.writeObject(b2);
        o.close();
        // Now get them back:
        @SuppressWarnings("resource")
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                "Blips.out"));
        System.out.println("Recovering b1:");
        b1 = (Blip1) in.readObject();
        // 出现异常
        // ! System.out.println("Recovering b2:");
        // ! b2 = (Blip2)in.readObject();
    }
}
