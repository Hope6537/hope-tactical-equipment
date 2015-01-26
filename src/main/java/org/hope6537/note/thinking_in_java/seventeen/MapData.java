package org.hope6537.note.thinking_in_java.seventeen;


import org.hope6537.note.thinking_in_java.fifteen.Generator;

import java.util.LinkedHashMap;

/**
 * @param <K>
 * @param <V>
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe Map生成器
 * @signdate 2014-7-22下午03:35:16
 * @company Changchun University&SHXT
 */
public class MapData<K, V> extends LinkedHashMap<K, V> {
    /**
     * @describe
     */
    private static final long serialVersionUID = -2083780903878303606L;

    public MapData(Generator<Pair<K, V>> gen, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Pair<K, V> p = gen.next();
            put(p.key, p.value);
        }
    }

    public MapData(Generator<K> key, Generator<V> value, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(key.next(), value.next());
        }
    }

    public MapData(Generator<K> key, V value, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(key.next(), value);
        }
    }

    public MapData(Iterable<K> genK, Generator<V> genV) {
        for (K key : genK) {
            put(key, genV.next());
        }
    }

    public MapData(Iterable<K> genK, V value) {
        for (K key : genK) {
            put(key, value);
        }
    }

    public static <K, V> MapData<K, V> map(Generator<Pair<K, V>> gen,
                                           int quantity) {
        return new MapData<K, V>(gen, quantity);
    }

    public static <K, V> MapData<K, V> map(Generator<K> genK,
                                           Generator<V> genV, int quantity) {
        return new MapData<K, V>(genK, genV, quantity);
    }

    public static <K, V> MapData<K, V> map(Generator<K> genK, V value,
                                           int quantity) {
        return new MapData<K, V>(genK, value, quantity);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> genK, Generator<V> genV) {
        return new MapData<K, V>(genK, genV);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> genK, V value) {
        return new MapData<K, V>(genK, value);
    }

}
