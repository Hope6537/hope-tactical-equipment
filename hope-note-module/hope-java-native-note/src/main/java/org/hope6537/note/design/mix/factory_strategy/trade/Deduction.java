package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 扣款策略接口
 */
public interface Deduction {

    public boolean exec(Card card, Trade trade);
}
