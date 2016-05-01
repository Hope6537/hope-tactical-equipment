package org.hope6537.note.tij.sixteen;

import org.hope6537.note.tij.fifteen.Generator;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 计数生成器
 * @signdate 2014-7-22下午02:53:49
 * @company Changchun University&SHXT
 */
public class CountingGenerator {

    static char[] chars = ("abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 内部Boolean类型生成器
     * @signdate 2014-7-22下午02:53:57
     * @company Changchun University&SHXT
     */
    public static class Boolean implements Generator<java.lang.Boolean> {
        private boolean value = false;

        public java.lang.Boolean next() {
            value = !value; // Just flips back and forth
            return value;
        }
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 内部Byte类型生成器
     * @signdate 2014-7-22下午02:54:13
     * @company Changchun University&SHXT
     */
    public static class Byte implements Generator<java.lang.Byte> {
        private byte value = 0;

        public java.lang.Byte next() {
            return value++;
        }
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 字符生成器
     * @signdate 2014-7-22下午02:54:21
     * @company Changchun University&SHXT
     */
    public static class Character implements Generator<java.lang.Character> {
        int index = -1;

        public java.lang.Character next() {
            index = (index + 1) % chars.length;
            return chars[index];
        }
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 字符串生成器
     * @signdate 2014-7-22下午02:54:30
     * @company Changchun University&SHXT
     */
    public static class String implements Generator<java.lang.String> {
        /**
         * @describe 随机化字符
         */
        Generator<java.lang.Character> cg = new Character();
        private int length = 7;

        public String() {
        }

        public String(int length) {
            this.length = length;
        }

        /* (non-Javadoc) 通过拼接的方式生成新字符串
         * @see org.hope6537.note.tij.fifteen.Generator#next()
         * @author:Hope6537(赵鹏)
         */
        public java.lang.String next() {
            char[] buf = new char[length];
            for (int i = 0; i < length; i++)
                buf[i] = cg.next();
            return new java.lang.String(buf);
        }
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe Short生成器
     * @signdate 2014-7-22下午02:54:39
     * @company Changchun University&SHXT
     */
    public static class Short implements Generator<java.lang.Short> {
        private short value = 0;

        public java.lang.Short next() {
            return value++;
        }
    }

    /**
     * @author Hope6537(赵鹏)
     * @version 0.9
     * @describe 整数生成器
     * @signdate 2014-7-22下午02:55:13
     * @company Changchun University&SHXT
     */
    public static class Integer implements Generator<java.lang.Integer> {
        private int value = 0;

        public java.lang.Integer next() {
            return value++;
        }
    }

    public static class Long implements Generator<java.lang.Long> {
        private long value = 0;

        public java.lang.Long next() {
            return value++;
        }
    }

    public static class Float implements Generator<java.lang.Float> {
        private float value = 0;

        public java.lang.Float next() {
            float result = value;
            value += 1.0;
            return result;
        }
    }

    public static class Double implements Generator<java.lang.Double> {
        private double value = 0.0;

        public java.lang.Double next() {
            double result = value;
            value += 1.0;
            return result;
        }
    }
} 
