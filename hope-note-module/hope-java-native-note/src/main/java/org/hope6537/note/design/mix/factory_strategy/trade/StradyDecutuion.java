package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 具体扣款策略实现类
 */
public class StradyDecutuion implements Deduction {

    @Override
    public boolean exec(Card card, Trade trade) {
        //固定金额和自由金额各扣除50%
        int halfMoney = (int) Math.rint(trade.getAmount() / 2.0);
        card.setFreeMoney(card.getFreeMoney() - halfMoney);
        card.setSteadyMoney(card.getSteadyMoney() - halfMoney);
        return true;
    }
}
