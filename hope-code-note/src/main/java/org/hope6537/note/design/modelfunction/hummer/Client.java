package org.hope6537.note.design.modelfunction.hummer;

import org.junit.Test;

public class Client {

    @Test
    public void test() {
        AbstractHummerModel model = new HummerH1Model();
        model.run();
    }


}
