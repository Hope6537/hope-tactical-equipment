# encoding:UTF-8
import os


def generate(objectName, columns):
    params = ""
    validation = ''
    nextStep = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
        validation += 'checkNotNull(' + c[0] + ', "[添加失败][当前插入数据字段(' + c[0] + ')为空]");\n'
        nextStep += c[0] + ','

    params = params[0:-1]
    nextStep = nextStep[0:-1]

    text = """
package org.hope6537.controller;

import org.springframework.web.bind.annotation.*;
import com.google.common.collect.Lists;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.annotation.WatchedAuthRequest;
import org.hope6537.annotation.WatchedNoAuthRequest;
import org.hope6537.dto.{ObjectName}Dto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.rest.utils.ResponseDict;
import org.hope6537.service.{ObjectName}Service;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 默认RESTFul API实现类
 * Created by hope6537 by Code Generator
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/{objectName}/")
@EnableAutoConfiguration
public class {ObjectName}Controller {

    @Resource(name = "{objectName}Service")
    private {ObjectName}Service {objectName}Service;

    @WatchedAuthRequest
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public Response post{ObjectName}Data(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            {ObjectName}Dto {objectName}Dto = dataMap.getObject("postObject", {ObjectName}Dto.class);
            //判断{objectName}Dto是否合法,还需要校验其他字段
            checkNotNull({objectName}Dto, ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = {objectName}Service.add{ObjectName}({objectName}Dto);
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @WatchedAuthRequest
    @RequestMapping(value = "put", method = RequestMethod.PUT)
    @ResponseBody
    public Response put{ObjectName}Data(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            {ObjectName}Dto {objectName}Dto = dataMap.getObject("putObject", {ObjectName}Dto.class);
            //判断{objectName}Dto是否合法,还需要校验其他字段
            checkNotNull({objectName}Dto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull({objectName}Dto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = {objectName}Service.modify{ObjectName}({objectName}Dto);
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }

    @WatchedAuthRequest
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete{ObjectName}Data(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            {ObjectName}Dto {objectName}Dto = dataMap.getObject("deleteObject", {ObjectName}Dto.class);
            //判断{objectName}Dto是否合法,还需要校验其他字段
            checkNotNull({objectName}Dto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull({objectName}Dto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = {objectName}Service.remove{ObjectName}({objectName}Dto.getId());
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }

    @WatchedNoAuthRequest
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public Response get{ObjectName}Data(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            {ObjectName}Dto {objectName}Dto = dataMap.getObject("{objectName}", {ObjectName}Dto.class);
            //判断{objectName}Dto是否合法,还需要校验其他字段
            checkNotNull({objectName}Dto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull({objectName}Dto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<{ObjectName}Dto> result = {objectName}Service.get{ObjectName}ById({objectName}Dto.getId());
            return Response.getInstance(result.isSuccess()).addAttribute("result", result.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }

    @WatchedNoAuthRequest
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    @ResponseBody
    public Response fetch{ObjectName}Data(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //验证完成,开始查询
            {ObjectName}Dto query = PageMapUtil.getQuery(dataMap.getString("fetchObject"),,dataMap.getString("pageMap"), {ObjectName}Dto.class);
            ResultSupport<List<{ObjectName}Dto>> {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(query);
            return Response.getInstance({objectName}ListByQuery.isSuccess())
                    .addAttribute("result", {objectName}ListByQuery.getModule())
                    .addAttribute("isEnd", {objectName}ListByQuery.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./controller/" + objectName + "Controller.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName
