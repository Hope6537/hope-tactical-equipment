package org.hope6537.hadoop.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
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
import java.util.NavigableMap;

/**
 * Created by Hope6537 on 2015/2/6.
 */
public class HBaseModel implements Serializable {

    private Integer rowKey;

    private static Logger logger;

    private static final Integer NORMAL_SIZE = 2;
    private static final String USERNAME_COLUMN = "username";
    private static final String PASSWORD_COLUMN = "password";
    private static final String INFO_MAP = "info";
    private static final String SECURITY_MAP = "security";

    private HBaseFamilyMap hBaseFamilyMap;

    public HBaseFamilyMap gethBaseFamilyMap() {
        return hBaseFamilyMap;
    }

    public void sethBaseFamilyMap(HBaseFamilyMap hBaseFamilyMap) {
        this.hBaseFamilyMap = hBaseFamilyMap;
    }

    public static HBaseModel getModelFromHBase(Result result) {
        HBaseModel model = getInstance();
        HBaseFamilyMap baseFamilyMap = model.gethBaseFamilyMap();
        NavigableMap<byte[], NavigableMap<byte[], byte[]>> resultMap = result.getNoVersionMap();
        for (byte[] keyByte : resultMap.keySet()) {
            String key = Bytes.toString(keyByte);
            Map<String, Object> familyMap = baseFamilyMap.get(key);
            if (ApplicationConstant.isNull(familyMap)) {
                familyMap = new HashMap<>();
            }
            NavigableMap<byte[], byte[]> subMap = resultMap.get(keyByte);
            for (byte[] subKeyByte : subMap.keySet()) {
                String columnKey = Bytes.toString(subKeyByte);
                String value = Bytes.toString(subMap.get(subKeyByte));
                familyMap.put(columnKey, value);
            }
            baseFamilyMap.put(key, familyMap);
        }
        model.sethBaseFamilyMap(baseFamilyMap);
        return model;
    }

    public Object getValueByFamilyAndColumn(String familyName, String columnName) {
        if (!(ApplicationConstant.notNull(familyName) && ApplicationConstant.notNull(columnName))) {
            logger.warn("data not access");
            return null;
        } else {
            try {
                HBaseFamilyMap map = gethBaseFamilyMap();
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
            HBaseFamilyMap map = gethBaseFamilyMap();
            Map<String, Object> familyMap = map.get(familyName);
            if (ApplicationConstant.isNull(familyMap)) {
                familyMap = new HashMap<>();
            }
            familyMap.put(columnName, data);
        }
        return true;

    }

    private HBaseModel() {
        this(null);
    }

    private HBaseModel(List<String> columnFamilyNames) {
        hBaseFamilyMap = new HBaseFamilyMap();
        hBaseFamilyMap.put(INFO_MAP, null);
        hBaseFamilyMap.put(SECURITY_MAP, null);
        if (ApplicationConstant.isNull(columnFamilyNames)) {
            return;
        }
        for (String columnFamilyName : columnFamilyNames) {
            hBaseFamilyMap.put(columnFamilyName, null);
        }
    }


    private static HBaseModel getInstance(List<String> columnFamilyNames) {
        HBaseModel model = new HBaseModel(columnFamilyNames);
        //TODO:init
        return model;
    }

    private static HBaseModel getInstance() {
        HBaseModel model = new HBaseModel();
        return model;
    }


    public static HBaseModel castToModelBasic(Object object) {
        HBaseModel hBaseModel = getInstance();
        HBaseFamilyMap baseModelHashMap = hBaseModel.gethBaseFamilyMap();
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
        return hBaseModel;
    }

    public static <T> T castToObject(HBaseModel hBaseModel, T obj) throws ClassNotFoundException {
        if (ApplicationConstant.isNull(hBaseModel)) {
            return null;
        } else if (ApplicationConstant.isNull(obj)) {
            throw new NullPointerException("not able to cast null object");
        } else {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> clz = classLoader.loadClass(obj.getClass().getName());
            HBaseFamilyMap hbaseModelHashMap = hBaseModel.gethBaseFamilyMap();
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
        HBaseFamilyMap hbaseModelHashMap = gethBaseFamilyMap();
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
