package org.hope6537.note.tij.eighteen;

import java.nio.*;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 視圖緩衝器實例
 * @signdate 2014年7月24日下午7:37:39
 * @company Changchun University&SHXT
 */
public class ViewBuffers {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer
                .wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});
        bb.rewind();
        printnb("Byte Buffer ");
        while (bb.hasRemaining())
            printnb(bb.position() + " -> " + bb.get() + ", ");
        print();
        CharBuffer cb = ((ByteBuffer) bb.rewind()).asCharBuffer();
        printnb("Char Buffer ");
        while (cb.hasRemaining())
            printnb(cb.position() + " -> " + cb.get() + ", ");
        print();
        FloatBuffer fb = ((ByteBuffer) bb.rewind()).asFloatBuffer();
        printnb("Float Buffer ");
        while (fb.hasRemaining())
            printnb(fb.position() + " -> " + fb.get() + ", ");
        print();
        IntBuffer ib = ((ByteBuffer) bb.rewind()).asIntBuffer();
        printnb("Int Buffer ");
        while (ib.hasRemaining())
            printnb(ib.position() + " -> " + ib.get() + ", ");
        print();
        LongBuffer lb = ((ByteBuffer) bb.rewind()).asLongBuffer();
        printnb("Long Buffer ");
        while (lb.hasRemaining())
            printnb(lb.position() + " -> " + lb.get() + ", ");
        print();
        ShortBuffer sb = ((ByteBuffer) bb.rewind()).asShortBuffer();
        printnb("Short Buffer ");
        while (sb.hasRemaining())
            printnb(sb.position() + " -> " + sb.get() + ", ");
        print();
        DoubleBuffer db = ((ByteBuffer) bb.rewind()).asDoubleBuffer();
        printnb("Double Buffer ");
        while (db.hasRemaining())
            printnb(db.position() + " -> " + db.get() + ", ");
    }

    private static void print() {
        System.out.println();

    }

    private static void printnb(String string) {
        System.out.print(string);
    }
} /*
 * Output: Byte Buffer 0 -> 0, 1 -> 0, 2 -> 0, 3 -> 0, 4 -> 0, 5 -> 0, 6 -> 0, 7
 * -> 97, Char Buffer 0 -> , 1 -> , 2 -> , 3 -> a, Float Buffer 0 -> 0.0, 1 ->
 * 1.36E-43, Int Buffer 0 -> 0, 1 -> 97, Long Buffer 0 -> 97, Short Buffer 0 ->
 * 0, 1 -> 0, 2 -> 0, 3 -> 97, Double Buffer 0 -> 4.8E-322,
 */// :~
