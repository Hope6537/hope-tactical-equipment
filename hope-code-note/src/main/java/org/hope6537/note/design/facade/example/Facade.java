package org.hope6537.note.design.facade.example;

/**
 * 门面对象
 * 门面对象不参与子系统的业务逻辑
 */
public class Facade {

    private ClassA classA = new ClassA();
    private ClassB classB = new ClassB();
    private ClassC classC = new ClassC();

    public void methodA() {
        System.out.println("logger");
        classA.doSomethingA();
    }

    public void methodB() {
        System.out.println("logger");
        classB.doSomethingB();
    }

    public void methodC() {
        System.out.println("logger");
        classC.doSomethingC();
    }


}
