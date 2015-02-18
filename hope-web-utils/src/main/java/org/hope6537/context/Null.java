package org.hope6537.context;

import java.util.List;

public class Null {

    public static boolean hasEmpty(List<Object> keys) {
        boolean flag = false;
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == null) {
                flag = true;
                return flag;
            } else {
                if (keys.get(i).toString().isEmpty()) {
                    flag = true;
                    return flag;
                }
            }
        }
        return flag;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            if (str.isEmpty()) {
                return true;
            }
        }
        return false;


    }

    public static int randomInt() {
        int a = (int) (Math.random() * 90);
        if (a >= 60) {
            return 1;
        } else if (a > 30 && a < 60) {
            return 0;
        } else {
            return -1;
        }

    }
}
