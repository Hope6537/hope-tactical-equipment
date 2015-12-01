package org.hope6537.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import java.io.*;
import java.util.Map;

/**
 * Velocity Engine Helper
 */
public class VelocityHelper {
    private static final VelocityHelper instance = new VelocityHelper();

    private VelocityHelper() {
        //初始化velocity的信息 主要设置一些Velocity的默认属性  
        try {
            Velocity.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get Instance
     */
    public static VelocityHelper getInstance() {
        return instance;
    }

    /**
     * Render : reader -> writer
     *
     * @param context
     * @param writer
     * @param reader
     * @return
     */
    public boolean evaluate(Context context, Writer writer, Reader reader) {
        try {
            return Velocity.evaluate(context, writer, "", reader);
        } catch (Exception e) {
            throw new RuntimeException("velocity evaluate error! detail [" + e.getMessage() + "]");
        }
    }

    /**
     * filter to writer
     *
     * @param map
     * @param reader
     * @return
     */
    @SuppressWarnings("unchecked")
    public Writer evaluateToWriter(Map map, Reader reader, String targetFilePath) {
        try {
            VelocityContext context = convertVelocityContext(map);
            PrintWriter writer = new PrintWriter(targetFilePath);
            //开始评估  
            this.evaluate(context, writer, reader);
            return writer;
        } catch (Exception e) {
            throw new RuntimeException("velocity evaluate error! detail [" + e.getMessage() + "]");
        }
    }

    /**
     * getProperty
     *
     * @param key
     * @return
     */
    public Object getProperty(String key) {
        return Velocity.getProperty(key);
    }

    /**
     * convert the Velocity Context
     */
    private VelocityContext convertVelocityContext(Map<String, Object> map) {
        VelocityContext context = new VelocityContext();
        if (map == null) {
            return context;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        return context;
    }

}  