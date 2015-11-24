/*
 * Copyright 1999-2101 Alibaba.com. All rights reserved.
 * This software is the confidential and proprietary information of Alibaba.com ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Alibaba.com.
 */
package org.hope6537.mapping;

import org.dozer.CustomConverter;
import org.hope6537.serialize.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author luqing.zz
 */
public class TpgMsgContentConverter implements CustomConverter {

    private static final Logger logger = LoggerFactory.getLogger(TpgMsgContentConverter.class);

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if (sourceFieldValue == null) return null;
        if (sourceClass == String.class) { // String -> Object
            return strToObject(sourceFieldValue, destinationClass);
        } else if (Serializable.class.isAssignableFrom(sourceClass)) { // Object -> String
            return objectToStr(sourceFieldValue);
        }
        return null;
    }

    private static Object objectToStr(Object sourceFieldValue) {
        return SerializeUtil.encodeObject(sourceFieldValue);
    }

    private static Object strToObject(Object sourceFieldValue, Class<?> destinationClass) {
        try {
            return SerializeUtil.decodeObject((String) sourceFieldValue, destinationClass);
        } catch (Exception e) {
            logger.error("Failed to decode object. ", e);
            return null;
        }
    }

}
