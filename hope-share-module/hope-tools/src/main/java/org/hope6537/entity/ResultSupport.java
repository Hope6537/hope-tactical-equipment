package org.hope6537.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class ResultSupport<T> implements Serializable {

    public static final String AugumentIsNull = "augument is null";

    /**
     * 备注
     */
    private String remark;

    /**
     * 返回编号
     */
    private String resultCode;

    /**
     * 是否成功
     */
    private boolean success = false;

    /**
     * 装载对象
     */
    private T module;

    private long totalCount;

    /**
     * 异常
     */
    private Exception exception;

    public static <T> ResultSupport<T> getInstance(boolean expr, String resultCode, String msg, T module) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.setSuccess(expr);
        result.setRemark(msg);
        result.setModule(module);
        result.setResultCode(resultCode);
        return result;
    }

    public static <T> ResultSupport<T> getInstance(boolean expr, String msg, T module) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.setSuccess(expr);
        result.setRemark(msg);
        result.setModule(module);
        if (expr) {
            result.setResultCode("1001");
        } else {
            result.setResultCode("4004");
        }

        return result;
    }

    public static <T> ResultSupport<T> getInstance(boolean expr, String msg) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.setSuccess(expr);
        result.setRemark(msg);
        if (expr) {
            result.setResultCode("1001");
        } else {
            result.setResultCode("4004");
        }

        return result;
    }

    public static <T> ResultSupport<T> getInstance(Exception e) {
        ResultSupport<T> result = new ResultSupport<T>();
        result.setSuccess(false);
        String msg = StringUtils.isBlank(e.getMessage()) ? "系统异常" : e.getMessage();
        result.setRemark(msg);
        result.setException(e);
        return result;
    }

    @SuppressWarnings("unchecked")
    public <O> ResultSupport<O> castToReturnFailed(Class<O> clz) {
        if (this.isSuccess()) {
            throw new RuntimeException("结果正常,不允许转换");
        }
        this.module = null;
        return (ResultSupport<O>) this;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
