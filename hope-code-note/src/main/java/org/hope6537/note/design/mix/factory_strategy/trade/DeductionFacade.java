package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 扣款模块封装
 */
public class DeductionFacade {

    public static Card deduct(Card card, Trade trade) {
        Strategics strategics = getDeductionType(trade);
        Deduction deduction = StrategyFactory.getDeduction(strategics);
        DeductionContext context = new DeductionContext(deduction);
        context.exec(card, trade);
        return card;
    }

    private static Strategics getDeductionType(Trade trade) {
        if (trade.getTradeNo().contains("admin")) {
            return Strategics.FreeDeduction;
        } else {
            return Strategics.StradyDecutuion;
        }
    }


}
