package org.hope6537.note.design.flyweight.example;

import java.util.HashMap;

/**
 * 享元工厂
 */
public class FlyweightFactory {

    private static HashMap<String, AbstractFlyweight> pool = new HashMap<>();

    public static AbstractFlyweight getFlyweight(String extrinsic) {

        AbstractFlyweight abstractFlyweight = null;

        if (pool.containsKey(extrinsic)) {
            abstractFlyweight = pool.get(extrinsic);
        } else {
            //根据外部状态创建享元对象
            abstractFlyweight = new FlyWeight1(extrinsic);
            //abstractFlyweight = new FlyWeight2(extrinsic);
            pool.put(extrinsic, abstractFlyweight);
        }
        return abstractFlyweight;
    }
}
