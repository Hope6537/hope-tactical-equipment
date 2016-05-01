package org.hope6537.note.thinking_in_java.fifteen;

class Other {

}

/**
 * @version 0.9
 * @Describe 可以使用任何类型作为其泛型参数
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:10:48
 * @company Changchun University&SHXT
 */
class BasicOther extends BasicHolder<Other> {

}

/**
 * @version 0.9
 * @Describe 自限定 强制泛型当做其自己的边界参数来使用
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:10:33
 * @company Changchun University&SHXT
 */
public class Unconstrained {
    public static void main(String[] args) {
        BasicOther b = new BasicOther();
        BasicOther b2 = new BasicOther();
        b.setElement(new Other());
        Other o = b.getElement();
        b.f();
    }
    /*
     * output: other
	 */
}
