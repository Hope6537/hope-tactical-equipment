/*
 * Copyright 1999-2101 Alibaba.com. All rights reserved.
 * This software is the confidential and proprietary information of Alibaba.com ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Alibaba.com.
 */
package org.hope6537.serialize;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * @author wuyang.zp
 */
public abstract class SerializeUtil {

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(baos);
            closeQuietly(oos);
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            Object object = ois.readObject();
            return clazz.cast(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(bais);
            closeQuietly(ois);
        }
    }

    public static String encodeObject(Object obj) {
        byte[] bytes = serialize(obj);
        byte[] zipped = CompressUtil.zip(bytes);
        return new String(Base64.encodeBase64(zipped));
    }

    public static <T> T decodeObject(String base64, Class<T> clazz) {
        byte[] zipped = Base64.decodeBase64(base64.getBytes());
        byte[] unzipped = CompressUtil.unzip(zipped);
        return deserialize(unzipped, clazz);
    }

    private static void closeQuietly(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException e) {
        }
    }
}
