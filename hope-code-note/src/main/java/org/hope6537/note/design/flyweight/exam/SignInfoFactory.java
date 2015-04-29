package org.hope6537.note.design.flyweight.exam;

import java.util.HashMap;

/**
 * 带对象池的工厂类
 */
public class SignInfoFactory {

    private static HashMap<String, SignInfo> pool = new HashMap<>();

    public static SignInfo getSignInfo(String key) {
        SignInfo result = null;
        if (!pool.containsKey(key)) {
            System.out.printf("建立[%s]号对象,并放入池中\r\n", key);
            result = new SignInfo4Pool(key);
            pool.put(key, result);
        } else {
            result = pool.get(key);
            System.out.printf("直接从池中获得[%s]号对象\r\n", key);
        }
        assert result != null;
        return result;
    }


}
