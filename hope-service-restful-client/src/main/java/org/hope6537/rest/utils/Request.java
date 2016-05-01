package org.hope6537.rest.utils;

import java.util.Map;

/**
 * 请求承载对象
 */
public class Request {

    /**
     * 验证用token字符串
     */
    private String token;

    /**
     * 分页指示器字符串
     */
    private String pageMap;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 传输参数
     */
    private Map<String, Object> data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPageMap() {
        return pageMap;
    }

    public void setPageMap(String pageMap) {
        this.pageMap = pageMap;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
