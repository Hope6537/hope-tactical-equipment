package org.hope6537.note.design.mediator.stroage;

/**
 * 具体中介者
 */
public class Mediator extends AbstractMediator {


    @Override
    public void execute(String commandStr, Object... objects) {

    }

    /**
     * 业务1 采购电脑
     */
    private void buyComputer(int number) {
        int saleStatus = super.sale.getSaleStatus();
        if (saleStatus > 80) {
            System.out.println("销售良好，继续采购 " + number);
            super.stock.increase(number);
        } else {
            int buyNumber = number / 2;
            System.out.println("销售一半，继续采购 " + buyNumber);
        }
    }

    /**
     * 业务2 销售电脑
     */
    private void sellComputer(int number) {
        //库存不够，在这里进行协调
        if (super.stock.getStockNumber() < number) {
            super.purchase.buyIBMComputer(number);
        }
        super.stock.decrease(number);
    }

    /**
     * 业务3 折价销售电脑
     */
    private void offSell() {
        System.out.println("折价销售" + stock.getStockNumber());
    }

    /**
     * 业务4 清仓处理
     */
    private void clearStock() {
        super.sale.offSale();
        super.purchase.refuseBuyIBM();
    }
}
