package org.hope6537.note.design.memento.multi;


/**
 * 多状态备忘录模式场景类
 */
public class Client {


    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        originator.setState1("11");
        originator.setState2("22");
        originator.setState3("33");
        System.out.println("状态1" + originator.toString());
        //===[change]
        caretaker.setMemento(originator.createMemento());
        originator.setState1("aa");
        originator.setState2("bb");
        originator.setState3("cc");
        System.out.println("状态2" + originator.toString());
        originator.restoreMemento(caretaker.getMemento());
        System.out.println("状态1" + originator.toString());

    }


}
