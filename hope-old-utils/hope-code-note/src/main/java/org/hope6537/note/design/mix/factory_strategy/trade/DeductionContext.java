package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 对于策略的封装
 */
public class DeductionContext {

    private Deduction deduction;

    public DeductionContext(Deduction deduction) {
        this.deduction = deduction;
    }

    public boolean exec(Card card, Trade trade) {
        return this.deduction.exec(card, trade);
    }
}
