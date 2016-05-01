package org.hope6537.entity;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Response，用于响应请求
 * Created by wuyang.zp on 2015/5/17
 */
public class Response implements Serializable {

    private static final String SUCCESS_MSG = "%E6%93%8D%E4%BD%9C%E6%88%90%E5%8A%9F";
    private static final String ERROR_MSG = "%E6%93%8D%E4%BD%9C%E5%A4%B1%E8%B4%A5";
    private Logger LOGGER = LoggerFactory.getLogger(Response.class);
    private boolean success;
    private String returnMsg;
    private Map<String, Object> data;

    private Response(boolean result) {
        success = result;
        returnMsg = success ? SUCCESS_MSG : ERROR_MSG;
        data = Maps.newHashMap();
    }

    public static Response getInstance(boolean result) {
        return new Response(result);
    }

    public Response addAttribute(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public Response setReturnMsg(String returnMsg) {
        setReturnMsg(returnMsg, "UTF-8");
        return this;
    }

    public String getReturnMsg(String encode) {
        try {
            return URLDecoder.decode(returnMsg, encode);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage() + " cause:" + e.getCause(), e);
        }
        return null;
    }

    public Response setReturnMsg(String returnMsg, String encode) {
        try {
            this.returnMsg = URLEncoder.encode(returnMsg, encode);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage() + " cause:" + e.getCause(), e);
        }
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        if (data == null) {
            data = Maps.newHashMap();
        }
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
