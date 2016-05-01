package org.hope6537.spring;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.hope6537.date.DateConvert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanMapUtil {

    public static Object Map2Bean(Class type, Map map) throws Exception {
        ConvertUtils.register(new DateConvert(), Date.class);
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object obj = type.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!(map.containsKey(propertyName.toUpperCase())))
                continue;
            try {
                Object value = map.get(propertyName.toUpperCase());
                BeanUtils.setProperty(obj, propertyName, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static Map bean2Map(Object bean) throws Exception {
        Class type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!(propertyName.equals("class"))) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null)
                    returnMap.put(propertyName, result);
                else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}