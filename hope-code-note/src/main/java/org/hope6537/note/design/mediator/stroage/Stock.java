package org.hope6537.note.design.mediator.stroage;

/**
 * 库存管理
 */
public class Stock extends AbstractColleague {

    public Stock(AbstractMediator mediator) {
        super(mediator);
    }

    private static int COMPUTER_NUMBER = 100;

    public void increase(int number) {
        COMPUTER_NUMBER += number;
        System.out.println("库存数量为" + number);
    }

    public void decrease(int number) {
        COMPUTER_NUMBER -= number;
        System.out.println("库存数量为" + number);
    }

    public int getStockNumber() {
        return COMPUTER_NUMBER;
    }

    /**
     * 这时候涉及到了依赖，要求采购停止，同时销售人员尽快销售
     */
    public void clearStock() {
        System.out.println("Clear " + COMPUTER_NUMBER);
        super.mediator.execute("stock.clear");
    }
}
