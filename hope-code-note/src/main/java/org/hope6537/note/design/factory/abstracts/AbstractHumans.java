package org.hope6537.note.design.factory.abstracts;

abstract class AbstractWhites implements Human {

    public void getColor() {
        System.out.println("White");
    }

    ;

    @Override
    public void talk() {
        System.out.println("White Talk");
    }

}

abstract class AbstractBlacks implements Human {

    public void getColor() {
        System.out.println("Black");
    }

    ;

    @Override
    public void talk() {
        System.out.println("Black Talk");
    }

}

abstract class AbstractYellows implements Human {

    public void getColor() {
        System.out.println("Yellow");
    }

    ;

    @Override
    public void talk() {
        System.out.println("Yellow Talk");
    }

}