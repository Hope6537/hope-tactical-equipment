/*
 * Copyright 1999-2013 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package org.hope6537.mapping;

import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 枚举类和数字的转换
 */
public class EnumCodeAndNumberConverter implements CustomConverter {

    private static final Logger logger = LoggerFactory.getLogger(EnumCodeAndNumberConverter.class);

    public Object convert(Object existingDestinationFieldValue,
                          Object sourceFieldValue, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if (sourceFieldValue == null) return null;
        //目标类是枚举，而数据源是String
        if (destinationClass.isEnum()
                && (sourceClass == Integer.class)) {
            try {
                Method m = destinationClass.getMethod("parseCode", int.class);
                m.setAccessible(true);
                //调用静态方法
                Object o = m.invoke(destinationClass, sourceFieldValue);
                return destinationClass.cast(o);
            } catch (Exception e) {
                logger.error("Dozer converter error.", e);
            }
        } else if ((destinationClass == Integer.class)
                && sourceClass.isEnum()) {
            try {
                //Method m = sourceClass.getMethod("getCode", (Class<?>)null);
                Method m = sourceClass.getMethod("getCode");
                m.setAccessible(true);
                return (int) m.invoke(sourceFieldValue, (Object[]) null);
            } catch (Exception e) {
                logger.error("Dozer converter error.", e);
            }
        }
        logger.error("Type conversion is not supported.");
        return null;
    }
}
