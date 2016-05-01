package org.hope6537.note.design.memento.multi;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类，用于反射获取状态信息
 * 将Bean的所有属性和值放入HashMap中
 */
public class BeanUtils {

    public static Map<String, Object> backupProp(Object bean) {
        Map<String, Object> result = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String fieldName = propertyDescriptor.getName();
                Method readMethod = propertyDescriptor.getReadMethod();
                Object invoke = readMethod.invoke(bean, new Object[]{});
                if (!fieldName.equalsIgnoreCase("class")) {
                    result.put(fieldName, invoke);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void restoreProp(Object bean, Map<String, Object> propMap) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String fieldName = descriptor.getName();
                if (propMap.containsKey(fieldName)) {
                    Method writeMethod = descriptor.getWriteMethod();
                    writeMethod.invoke(bean, new Object[]{propMap.get(fieldName)});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
