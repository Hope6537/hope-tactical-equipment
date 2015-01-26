package org.hope6537.note.java8;

/**
 * Created by threegrand1 on 14-12-31.
 */
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
