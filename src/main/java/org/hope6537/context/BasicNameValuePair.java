package org.hope6537.context;

import java.io.Serializable;

/**
 * Created by Hope6537 on 2015/1/21.
 */
public class BasicNameValuePair<K, V> implements Serializable {

    private K key;
    private V value;

    public BasicNameValuePair() {
    }

    public BasicNameValuePair(K key) {
        this.key = key;
    }

    public BasicNameValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
