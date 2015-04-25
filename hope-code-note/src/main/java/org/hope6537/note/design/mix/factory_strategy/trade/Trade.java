package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 交易类
 */
public class Trade {

    private String tradeNo = "";

    private int amount = 0;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
