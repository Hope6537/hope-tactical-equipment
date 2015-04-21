package org.hope6537.note.design.flyweight.example;

/**
 * 抽象享元角色
 */
public abstract class AbstractFlyweight {

    private String intrinsic;

    protected final String EXTRINSIC;

    protected AbstractFlyweight(String extrinsic) {
        EXTRINSIC = extrinsic;
    }

    /**
     * 定义内部业务操作
     */
    public abstract void operate();

    public String getIntrinsic() {
        return intrinsic;
    }

    public void setIntrinsic(String intrinsic) {
        this.intrinsic = intrinsic;
    }
}
