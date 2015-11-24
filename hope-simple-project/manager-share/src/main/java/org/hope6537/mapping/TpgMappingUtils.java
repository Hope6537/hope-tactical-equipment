package org.hope6537.mapping;

/**
 * Created by wuyang.zp on 2015/7/20.
 */
public interface TpgMappingUtils {

    <T> T doMap(Object source, Class<T> destinationClass);

}
