package org.hope6537.note.design.servant.cleanable;

/**
 */
public class Client {

    public static void main(String[] args) {
        Cleaner cleaner = new Cleaner();
        cleaner.clean(new KitChen());
        Cleaner gradener = new Cleaner();
        gradener.clean(new Garden());
        Cleaner tailer = new Cleaner();
        tailer.clean(new Cloth());
    }
}
