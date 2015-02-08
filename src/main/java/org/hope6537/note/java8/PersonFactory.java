package org.hope6537.note.java8;

/**
 * Created by threegrand1 on 14-12-31.
 */
public interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
