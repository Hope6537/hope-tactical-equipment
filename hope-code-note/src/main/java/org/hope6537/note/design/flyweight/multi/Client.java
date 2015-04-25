package org.hope6537.note.design.flyweight.multi;

/**
 * 场景类
 */
public class Client {

    public static void main(String[] args) {
        ExtrinsicState state1 = new ExtrinsicState("1", "Hangzhou");
        ExtrinsicState state2 = new ExtrinsicState("2", "Beijing");
        SignInfoFactory.getSignInfo(state1);
        SignInfoFactory.getSignInfo(state2);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            SignInfoFactory.getSignInfo(state2);
        }
        long tail = System.currentTimeMillis();
        System.out.println(tail - start + "ms");
    }

}
