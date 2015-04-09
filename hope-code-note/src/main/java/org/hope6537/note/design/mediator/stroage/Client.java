package org.hope6537.note.design.mediator.stroage;

import org.junit.Test;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class Client {

    @Test
    public void test() {

        AbstractMediator mediator = new Mediator();

        System.out.println("buying");
        Purchase purchase = new Purchase(mediator);
        purchase.buyIBMComputer(100);

        System.out.println("selling");
        Sale sale = new Sale(mediator);
        sale.sellIBMComputer(1);

        System.out.println("clear");
        Stock stock = new Stock(mediator);
        stock.clearStock();

    }

}
