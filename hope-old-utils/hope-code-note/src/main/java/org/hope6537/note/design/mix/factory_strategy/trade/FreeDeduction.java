package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 扣款策略
 */
public class FreeDeduction implements Deduction {
    @Override
    public boolean exec(Card card, Trade trade) {
        //直接从自由余额扣除
        card.setFreeMoney(card.getFreeMoney() - trade.getAmount());
        return true;
    }
}
