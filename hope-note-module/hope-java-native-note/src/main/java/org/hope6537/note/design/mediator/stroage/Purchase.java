package org.hope6537.note.design.mediator.stroage;

/**
 * 库存管理人员 -独立但需要依赖的业务类
 */
public class Purchase extends AbstractColleague {

    public Purchase(AbstractMediator mediator) {
        super(mediator);
    }

    public void buyIBMComputer(int number) {
        System.out.println("buy ibm " + number);
    }

    public void refuseBuyIBM() {
        System.out.println("not by ibm");
    }

}
