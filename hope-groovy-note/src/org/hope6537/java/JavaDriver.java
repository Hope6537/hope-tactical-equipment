package org.hope6537.java;

import org.hope6537.groovy.Person;

/**
 * Created by hope6537 on 15/12/21.
 * Any Question sent to hope6537@qq.com
 */
public class JavaDriver {


    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1L);
        person.setUsername("username");
        person.setPassword("password");
        System.out.println(person.toString());
    }

}
