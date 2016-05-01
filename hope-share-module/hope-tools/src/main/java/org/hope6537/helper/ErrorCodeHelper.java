package org.hope6537.helper;

/**
 * Created by hope6537 on 15/12/19.
 * Any Question sent to hope6537@qq.com
 */
public enum ErrorCodeHelper {

    VAR_NULL_ERROR(1000, "Variable cannot be null"),
    SQL_NULL_ERROR(1001, "Sql cannot be null");

    private int code;

    private String value;

    ErrorCodeHelper(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ErrorCodeHelper parseCode(int code) {
        for (ErrorCodeHelper errorCodeHelper : ErrorCodeHelper.values()) {
            if (errorCodeHelper.getCode() == code) {
                return errorCodeHelper;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
