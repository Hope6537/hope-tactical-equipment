package org.hope6537.note.effectivejava.chapter3;

import org.junit.Test;

import java.util.Comparator;

/**
 * 1、如果你程序是一个值类，且目前里面有非常明显的排序关系，那么就有必要实现Comparable接口
 */
public class ComparableTest {
    /**
     * Comparator接口在java8中的lambda表达式中大放光彩
     * 可以使用很多种调皮的方式来进行实现，例子如下
     * 我们只需要调用接口的compare方法就可以了
     */
    @Test
    public void testLambdaInComparable() {
        Comparator<String> comparator = (p1, p2) -> p1.charAt(0) - p2.charAt(0);
        int res = comparator.compare("abc", "def");
        System.out.println(res);
    }

    /**
     * 1、正式设计之前，我们应当保证设计出来的接口应当是具有自反性、可传递性
     * 2、如果一个类有多个关键域，那么怎样比较的顺序才是最关键的。
     * 3、我们没有必要使用-1,0,1这样的标示符，我们可以直接将值返回出去例如下面
     */

    class PhoneNumber implements Comparable<PhoneNumber> {
        int prefix;
        int number;
        int suffix;


        @Override
        public int compareTo(PhoneNumber o) {
            //这样直接返回值的情况有效降低代码，比起return -1这样的东西更好理解些
            if (prefix > o.prefix) {
                return prefix;
            } else {
                return o.prefix;
            }
            //实际上还能更加简化,但是三目表达式破坏了 a.compareTo(b) == 0 == a.equals(b)这个特性，所以不推荐使用
            //return number > o.number ? number : o.number;

            //而确切的说还有一种方法,这样就解决了部分问题，实际上一般情况下这样用的还是多一些
            //但是要考虑边界问题，如果两个数一个负数一个正数
            //很有可能会出现suffixDiff超出int范围的情况
//            int suffixDiff = suffix - o.suffix;
//            if (suffix != 0) {
//                return suffixDiff;
//            }
//            return 0;
        }
    }
}
