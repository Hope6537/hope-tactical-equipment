package org.hope6537.note.tij.eighteen;

import java.io.*;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 序列化和反序列化具体使用方法
 * @signdate 2014年7月25日下午4:53:57
 * @company Changchun University&SHXT
 */
public class Blip3 implements Externalizable {

    private int i;
    private String s; // No initialization

    public Blip3() {
        System.out.println("Blip3 Constructor");
        // s, i not initialized
    }

    public Blip3(String x, int a) {
        System.out.println("Blip3(String x, int a)");
        s = x;
        i = a;
        // s & i initialized only in non-default constructor.
    }

    public static void main(String[] args) throws IOException,
            ClassNotFoundException {
        System.out.println("Constructing objects:");
        Blip3 b3 = new Blip3("A String ", 47);
        System.out.println(b3);
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
                "Blip3.out"));
        System.out.println("Saving object:");
        o.writeObject(b3);
        o.close();
        // Now get it back:
        @SuppressWarnings("resource")
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                "Blip3.out"));
        System.out.println("Recovering b3:");
        b3 = (Blip3) in.readObject();
        System.out.println(b3);
    }

    public String toString() {
        return s + i;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip3.writeExternal");
        // 必须在这里声明两个对象的处理办法
        out.writeObject(s);
        out.writeInt(i);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        System.out.println("Blip3.readExternal");
        // 必须在这里声明两个对象的处理办法
        s = (String) in.readObject();
        i = in.readInt();
    }
} /*
 * Output: Constructing objects: Blip3(String x, int a) A String 47 Saving
 * object: Blip3.writeExternal Recovering b3: Blip3 Constructor
 * Blip3.readExternal A String 47
 */// :~

