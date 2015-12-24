package org.hope6537.groovy

import org.hope6537.java.People

/**
 * Created by hope6537 on 15/12/21.
 * Any Question sent to hope6537@qq.com
 */
class GroovyDriver {

    public static void main(String[] args) {
        People people = new People();
        people.setId(1L);
        people.setUsername("username");
        people.setPassword("password");
        println people.toString();
    }

}
