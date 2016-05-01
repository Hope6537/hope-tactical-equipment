package org.hope6537.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hope6537 on 16/1/30.
 */
public enum IsDeleted {

    NO(0, "未删除"), YES(1, "已删除");

    private static Map<Integer, IsDeleted> codeToEnum;

    static {
        codeToEnum = new HashMap<Integer, IsDeleted>();
        for (IsDeleted isDeleted : values()) {
            codeToEnum.put(isDeleted.getCode(), isDeleted);
        }
    }

    private Integer code;
    private String value;

    IsDeleted(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static IsDeleted parseCode(int code) {
        for (IsDeleted isDeleted : IsDeleted.values()) {
            if (isDeleted.getCode() == code) {
                return isDeleted;
            }
        }
        return null;
    }

    public static IsDeleted getInstance(int code) {
        return codeToEnum.get(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
