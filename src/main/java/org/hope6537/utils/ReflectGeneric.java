package org.hope6537.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射泛型类型工具类
 * (例如：ObjectClass<String,Integer>，String为索引0，Integer为索引1)
 *
 * @author Hope6537
 */
public class ReflectGeneric {
    /**
     * 获得参数化类型的泛型类型，取第一个参数的泛型类型，（默认去的第一个）
     *
     * @param clazz 参数化类型
     * @return 泛型类型
     */
    @SuppressWarnings("unchecked")
    public static Class getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 根据参数索引获得参数化类型的泛型类型，（通过索引取得）
     *
     * @param clazz 参数化类型
     * @param index 参数索引
     * @return 泛型类型
     */
    @SuppressWarnings("unchecked")
    public static Class getClassGenricType(
            final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
