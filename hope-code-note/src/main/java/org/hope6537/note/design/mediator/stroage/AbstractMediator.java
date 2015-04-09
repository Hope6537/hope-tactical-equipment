package org.hope6537.note.design.mediator.stroage;

public abstract class AbstractMediator {

    /*
    确定需要的依赖关系
     */
    protected Purchase purchase;
    protected Sale sale;
    protected Stock stock;

    public AbstractMediator() {
        this.purchase = new Purchase(this);
        this.sale = new Sale(this);
        this.stock = new Stock(this);
    }

    public AbstractMediator(Purchase purchase, Sale sale, Stock stock) {
        this.purchase = purchase;
        this.sale = sale;
        this.stock = stock;
    }

    public abstract void execute(String commandStr, Object... objects);

}
