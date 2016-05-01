package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 策略工厂
 */
public class StrategyFactory {

    public static Deduction getDeduction(Strategics strategics) {
        Deduction deduction = null;
        try {
            deduction = (Deduction) Class.forName(strategics.getValue()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deduction;
    }

}
