package org.hope6537.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

public class AjaxResponse {
    private ReturnState returnState = ReturnState.OK;
    private String returnMsg;
    private Map<String, Object> returnData = new HashMap<String, Object>();

    public AjaxResponse() {
        this(ReturnState.OK, "");
    }

    public AjaxResponse(String returnMsg) {
        this(ReturnState.OK, returnMsg);
    }

    public AjaxResponse(ReturnState returnState, String returnMsg) {
        this.returnState = returnState;
        this.returnMsg = returnMsg;
    }

    public AjaxResponse(String attributeName, Object attributeValue) {
        this();
        addAttribute(attributeName, attributeValue);

    }

    public AjaxResponse(BindingResult result) {
        this.returnState = ReturnState.ERROR;
        this.returnMsg = "";
        for (ObjectError objectError : result.getAllErrors())
            this.returnMsg += objectError.getDefaultMessage();
    }

    public static AjaxResponse getInstanceByResult(boolean result) {
        if (result) {
            return new AjaxResponse("操作成功！");
        }
        return new AjaxResponse(ReturnState.ERROR, "操作失败！");
    }

    public boolean isOk() {
        return this.returnState == ReturnState.OK;
    }

    public boolean isWarning() {
        return this.returnState == ReturnState.WARNING;
    }

    public boolean isError() {
        return this.returnState == ReturnState.ERROR;
    }

    public ReturnState getReturnState() {
        return this.returnState;
    }

    public void setReturnState(ReturnState returnState) {
        this.returnState = returnState;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Map<String, Object> getReturnData() {
        if (this.returnData == null) {
            this.returnData = new HashMap();
        }
        return this.returnData;
    }

    public void setReturnData(Map<String, Object> returnData) {
        this.returnData = returnData;
    }

    public AjaxResponse addAttribute(String attributeName, Object attributeValue) {
        getReturnData().put(attributeName, attributeValue);
        return this;
    }

    public AjaxResponse addReturnMsg(String returnMsg) {
        this.setReturnMsg(returnMsg);
        return this;
    }


}


