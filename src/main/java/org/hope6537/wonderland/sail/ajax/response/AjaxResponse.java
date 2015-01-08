package org.hope6537.wonderland.sail.ajax.response;

import org.hope6537.wonderland.sail.constant.Constant;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

/**
 * Ajax返回值
 *
 * @author gaoxinyu
 */
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
        returnState = ReturnState.ERROR;
        returnMsg = "";
        for (ObjectError objectError : result.getAllErrors()) {
            returnMsg += objectError.getDefaultMessage();
        }
    }

    public static AjaxResponse getInstanceByResult(boolean result) {
        if (result) {
            return new AjaxResponse(Constant.OPERATE_SUCCESS);
        } else {
            return new AjaxResponse(ReturnState.ERROR, Constant.OPERATE_ERROR);
        }
    }

    public boolean isOk() {
        return returnState == ReturnState.OK;
    }

    public boolean isWarning() {
        return returnState == ReturnState.WARNING;
    }

    public boolean isError() {
        return returnState == ReturnState.ERROR;
    }

    public ReturnState getReturnState() {
        return returnState;
    }

    public void setReturnState(ReturnState returnState) {
        this.returnState = returnState;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Map<String, Object> getReturnData() {
        if (returnData == null) {
            returnData = new HashMap<String, Object>();
        }
        return returnData;
    }

    public void setReturnData(Map<String, Object> returnData) {
        this.returnData = returnData;
    }

    public AjaxResponse addAttribute(String attributeName, Object attributeValue) {
        getReturnData().put(attributeName, attributeValue);
        return this;
    }

}
