package org.hope6537.groovy

import com.alibaba.fastjson.JSON

/**
 * Created by hope6537 on 15/12/21.
 * Any Question sent to hope6537@qq.com
 */
class Person {

    Long id;

    String username;

    String password;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
