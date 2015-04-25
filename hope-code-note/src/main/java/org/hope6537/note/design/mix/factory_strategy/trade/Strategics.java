package org.hope6537.note.design.mix.factory_strategy.trade;

/**
 * 策略枚举
 */
public enum Strategics {

    StradyDecutuion("org.hope6537.note.design.mix.factory_strategy.trade.StradyDecutuion"),
    FreeDeduction("org.hope6537.note.design.mix.factory_strategy.trade.FreeDeduction");

    private String value = "";

    Strategics(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
