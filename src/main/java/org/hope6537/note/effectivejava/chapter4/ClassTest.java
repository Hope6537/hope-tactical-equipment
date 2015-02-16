package org.hope6537.note.effectivejava.chapter4;

import org.hope6537.note.java8.Person;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by threegrand1 on 15-1-8.
 */
public class ClassTest {

    /**
     * 首先要解决的是一个小问题，如果一个对象会暴露出一个公有的数组
     * 那么这个数组应当是final的 但是问题是如果final与包含可变对象的引用，引用自身将无法修改
     * 但是引用的对象却可以被修改，也就是说数组的内部数据将会玩完
     * 例如下面这个数据
     */

    //长度非零的数组总是可变的，所以这种方法是错误的。
    //如果类具有这样的变量，那么客户端就能够修改数组内部的内容，这是大部分安全漏洞的根源
    public static final Person[] VALUES = {};

    @Test
    public void testClass() {
        //Complex complex = new Complex();//compile error
        Complex complex = Complex.valueOf(1, 1);
        Complex after = complex.add(Complex.valueOf(2, 2));
        assertNotEquals(after, complex);//状态未改变
    }


    //永远不要提供任何会修改对象状态的方法

    /**
     * 下面是解释，何为修改对象状态？
     * 所谓改变对象的状态，就是改变对象的属性的值，或者说由对象自己完成变化
     * 我们看看下面这个类，对于外部来的参数进行的加减乘除等方法，并不是我们通常常规写的
     * this.re =  this.re + c.re;
     * 最后返回自身这个对象，而是重新建立了一个新对象，并将计算结果赋值进去，实际上String就是这样的原理
     * 这样的方式被称为函数式做法，它保证了不可变对象永远只有一个状态
     * 那就是被创建时候的状态。这样保证了他是线程安全的。不会再并发过程中被破坏
     * 但是会面临一些大数值但是小幅度更改时候的性能问题
     * 这个时候就应该有一个可变配套类，可以作为不可变类创建之前的缓存。
     */

    /**
     * 所以说为了保证这样的特性，我们需要将其设定为final
     * 或者还有一种方法，也就是工厂方法来代替公有的构造器
     */
}

final class Complex {

    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }

    // Accessors with no corresponding mutators
    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    public Complex add(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex subtract(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex multiply(Complex c) {
        return new Complex(re * c.re - im * c.im,
                re * c.im + im * c.re);
    }

    public Complex divide(Complex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new Complex((re * c.re + im * c.im) / tmp,
                (im * c.re - re * c.im) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Complex))
            return false;
        Complex c = (Complex) o;
        // See page 43 to find out why we use compare instead of ==
        return Double.compare(re, c.re) == 0 &&
                Double.compare(im, c.im) == 0;
    }

    @Override
    public int hashCode() {
        int result = 17 + hashDouble(re);
        result = 31 * result + hashDouble(im);
        return result;
    }

    private int hashDouble(double val) {
        long longBits = Double.doubleToLongBits(re);
        return (int) (longBits ^ (longBits >>> 32));
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}
