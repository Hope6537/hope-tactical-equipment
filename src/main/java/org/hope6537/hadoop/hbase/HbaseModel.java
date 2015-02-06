package org.hope6537.hadoop.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.hope6537.context.ApplicationConstant;
import org.slf4j.Logger;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hope6537 on 2015/2/6.
 */
public class HbaseModel implements Serializable {

    private Integer rowKey;

    private static Logger logger;

    private static final Integer NORMAL_SIZE = 2;
    private static final String USERNAME_COLUMN = "username";
    private static final String PASSWORD_COLUMN = "password";
    private static final String INFO_MAP = "info";
    private static final String SECURITY_MAP = "security";

    private HbaseModelHashMap columnFamilyMap;

    public HbaseModelHashMap getColumnFamilyMap() {
        return columnFamilyMap;
    }

    public void setColumnFamilyMap(HbaseModelHashMap columnFamilyMap) {
        this.columnFamilyMap = columnFamilyMap;
    }

    public Object getValueByFamilyAndColumn(String familyName, String columnName) {
        if (!(ApplicationConstant.notNull(familyName) && ApplicationConstant.notNull(columnName))) {
            logger.warn("data not access");
            return null;
        } else {
            try {
                HbaseModelHashMap map = getColumnFamilyMap();
                return map.get(familyName).get(columnName);
            } catch (NullPointerException e) {
                logger.error("no data");
                return null;
            }
        }
    }

    public boolean setValueByFamilyAndColumn(String familyName, String columnName, Object data) {
        if (!(ApplicationConstant.notNull(familyName) && ApplicationConstant.notNull(columnName) && ApplicationConstant.notNull(data))) {
            logger.warn("data not access");
            return false;
        } else {
            HbaseModelHashMap map = getColumnFamilyMap();
            Map<String, Object> familyMap = map.get(familyName);
            if (ApplicationConstant.isNull(familyMap)) {
                familyMap = new HashMap<>();
            }
            familyMap.put(columnName, data);
        }
        return true;

    }

    private HbaseModel() {
        this(null);
    }

    private HbaseModel(List<String> columnFamilyNames) {
        columnFamilyMap = new HbaseModelHashMap();
        columnFamilyMap.put(INFO_MAP, null);
        columnFamilyMap.put(SECURITY_MAP, null);
        if (ApplicationConstant.isNull(columnFamilyNames)) {
            return;
        }
        for (String columnFamilyName : columnFamilyNames) {
            columnFamilyMap.put(columnFamilyName, null);
        }
    }


    private static HbaseModel getInstance(List<String> columnFamilyNames) {
        HbaseModel model = new HbaseModel(columnFamilyNames);
        //TODO:init
        return model;
    }

    private static HbaseModel getInstance() {
        HbaseModel model = new HbaseModel();
        return model;
    }


    public static HbaseModel castToModelBasic(Object object) {
        HbaseModel hbaseModel = getInstance();
        HbaseModelHashMap baseModelHashMap = hbaseModel.getColumnFamilyMap();
        Map<String, Object> infoMap = new HashMap<>();
        Map<String, Object> securityMap = null;
        Class clz = object.getClass();
        Field[] fields = clz.getFields();
        try {
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clz);
                Method getMethod = pd.getReadMethod();
                String fieldId = field.getName();
                Object value = getMethod.invoke(object);
                if (fieldId.contains(PASSWORD_COLUMN) || fieldId.contains(USERNAME_COLUMN)) {
                    if (ApplicationConstant.isNull(securityMap)) {
                        securityMap = new HashMap<>();
                    }
                    securityMap.put(fieldId, value);
                    continue;
                }
                infoMap.put(fieldId, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseModelHashMap.put(INFO_MAP, infoMap);
        if (ApplicationConstant.notNull(securityMap)) {
            baseModelHashMap.put(SECURITY_MAP, securityMap);
        }
        return hbaseModel;
    }

    public static <T> T castToObject(HbaseModel hbaseModel, T obj) throws ClassNotFoundException {
        if (ApplicationConstant.isNull(hbaseModel)) {
            return null;
        } else if (ApplicationConstant.isNull(obj)) {
            throw new NullPointerException("not able to cast null object");
        } else {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> clz = classLoader.loadClass(obj.getClass().getName());
            HbaseModelHashMap hbaseModelHashMap = hbaseModel.getColumnFamilyMap();
            for (Map<String, Object> map : hbaseModelHashMap.values()) {
                for (String key : map.keySet()) {
                    Field field = null;
                    try {
                        field = clz.getField(key);
                        field.set(obj, map.get(key));
                    } catch (NoSuchFieldException e) {
                        logger.error("no such field found on" + key);
                        continue;
                    } catch (IllegalAccessException e) {
                        logger.error("IllegalAccessException on key = " + key + " value = " + map.get(key));
                        continue;
                    }
                }
            }
        }
        return obj;
    }

    public Put toPut() {
        Put put = new Put(Bytes.toBytes(rowKey));
        HbaseModelHashMap hbaseModelHashMap = getColumnFamilyMap();
        for (String familyKey : hbaseModelHashMap.keySet()) {
            Map<String, Object> map = hbaseModelHashMap.get(familyKey);
            for (String key : map.keySet()) {
                put.add(Bytes.toBytes(familyKey), Bytes.toBytes(key), Bytes.toBytes(map.get(key).toString()));
            }
        }
        return put;
    }


    public Integer getRowKey() {
        return rowKey;
    }

    public void setRowKey(Integer rowKey) {
        this.rowKey = rowKey;
    }
}
