package org.hope6537.note.design.flyweight.multi;

import org.hope6537.note.design.flyweight.exam.SignInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 */
public class SignInfoFactory {

    private static Map<ExtrinsicState, SignInfo> map = new HashMap<>();

    public static SignInfo getSignInfo(ExtrinsicState key) {
        SignInfo result = null;
        if (!map.containsKey(key)) {
            result = new SignInfo();
            map.put(key, result);
        } else {
            result = map.get(key);
        }
        return result;
    }
}
