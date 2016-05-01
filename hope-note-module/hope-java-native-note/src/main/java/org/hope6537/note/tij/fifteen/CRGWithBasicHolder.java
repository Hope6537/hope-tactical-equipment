package org.hope6537.note.tij.fifteen;

/**
 * @version 0.9
 * @Describe 我在创建一个新类，他继承自一个泛型类型，这个泛型类型接受我的累的名字作为其参数
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:07:24
 * @company Changchun University&SHXT
 */
class Subtype extends BasicHolder<Subtype> {

}

/**
 * @param <T>
 * @version 0.9
 * @Describe 这他妈是啥
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:09:29
 * @company Changchun University&SHXT
 */
class SelfBounded<T extends SelfBounded<T>> {

}

/**
 * @version 0.9
 * @Describe 所谓的循环泛型
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:07:09
 * @company Changchun University&SHXT
 */
public class CRGWithBasicHolder {
    public static void main(String[] args) {
        Subtype st1 = new Subtype(), st2 = new Subtype();
        st1.setElement(st2);
        Subtype st3 = st1.getElement();
        //即使我进行了set泛型赋值操作，依旧还是Subtype的类型
        st1.f();
    }
}
