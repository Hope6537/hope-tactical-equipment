package org.hope6537.note.tij.fifteen;

interface FactoryI<T> {
    T create();
}

class Foo2<T> {
    private T x;

    public <F extends FactoryI<T>> Foo2(F factory) {
        x = factory.create();
    }

    public T getX() {
        return x;
    }
}

class IntegerFactory implements FactoryI<Integer> {
    @Override
    public Integer create() {
        return new Integer(0);
    }
}

class Widget {
    public static class Factory implements FactoryI<Widget> {
        @Override
        public Widget create() {
            return new Widget();
        }
    }
}

/**
 * @version 0.9
 * @Describe 采用显式工厂的方法
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午12:49:41
 * @company Changchun University&SHXT
 */
public class FactoryConstraint {
    public static void main(String[] args) {
        int a = new Foo2<Integer>(new IntegerFactory()).getX();
        System.out.println(a);
        System.out.println(new Foo2<Widget>(new Widget.Factory()));
    }
}
