package org.hope6537.note.tij.fifteen;

import java.util.Date;

class Basic1 {
    private String value;

    public void set(String val) {
        this.value = val;
    }

    public String get() {
        return value;
    }
}

class Decoratior extends Basic1 {
    protected Basic1 basic;

    public Decoratior(Basic1 basic) {
        super();
        this.basic = basic;
    }

    @Override
    public void set(String val) {
        basic.set(val);
    }

    @Override
    public String get() {
        return basic.get();
    }
}

class TimeStamped1 extends Decoratior {
    private final long timeStamp;

    public TimeStamped1(Basic1 basic) {
        super(basic);
        this.timeStamp = new Date().getTime();
    }

    public long getStamp() {
        return timeStamp;
    }
}

class SerialNumberImp1 extends Decoratior {
    private static long counter = 1;
    private final long serialNumber = counter++;

    public SerialNumberImp1(Basic1 basic) {
        super(basic);
    }

    public long getSerialNumber() {
        return serialNumber;
    }
}

/**
 * @version 0.9
 * @Describe 使用装饰器模式 产生自泛型的类包含所有方法，但是由使用装饰器所产生的对象类型是最后被装饰的类型，即变量声明的类型
 * 及最后我们只能操作最后一层被包装的类型的方法
 * 如图s2无法调用getStamp()方法一样
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-20下午01:29:23
 * @company Changchun University&SHXT
 */
public class Decoration {
    public static void main(String[] args) {
        TimeStamped1 t = new TimeStamped1(new Basic1());
        SerialNumberImp1 s = new SerialNumberImp1(new Basic1());
        //↓这两个为啥好使呢？
        TimeStamped1 t2 = new TimeStamped1(new SerialNumberImp1(new Basic1()));
        SerialNumberImp1 s2 = new SerialNumberImp1(new TimeStamped1(new Basic1()));

        System.out.println(s2.getSerialNumber());
    }
}
