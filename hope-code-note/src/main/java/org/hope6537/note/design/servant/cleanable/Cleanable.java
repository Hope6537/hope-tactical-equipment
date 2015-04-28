package org.hope6537.note.design.servant.cleanable;

/**
 *
 */
public interface Cleanable {

    public void cleaned();

}

class Garden implements Cleanable {

    @Override
    public void cleaned() {
        System.out.println("Garden cleaned");
    }
}

class KitChen implements Cleanable {

    @Override
    public void cleaned() {
        System.out.println("KitChen cleaned");
    }
}

class Cloth implements Cleanable {

    @Override
    public void cleaned() {
        System.out.println("Cloth cleaned");
    }
}
