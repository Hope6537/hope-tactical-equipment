package org.hope6537.java8;

import org.junit.Test;

/**
 * Created by threegrand1 on 14-12-31.
 */
public class PersonTest {

    @Test
    public void testCreatePerson() {
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
        System.out.println(person.toString());
    }
}
