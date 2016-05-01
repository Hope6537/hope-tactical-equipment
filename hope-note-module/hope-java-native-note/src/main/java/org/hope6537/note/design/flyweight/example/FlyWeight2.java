package org.hope6537.note.design.flyweight.example;

/**
 * 具体享元角色
 */
public class FlyWeight2 extends AbstractFlyweight {

    protected FlyWeight2(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate() {
        //业务逻辑处理
        System.out.println("享元模式业务2~");
    }

}
