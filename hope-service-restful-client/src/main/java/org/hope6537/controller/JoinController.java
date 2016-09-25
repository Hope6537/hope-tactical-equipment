package org.hope6537.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.annotation.WatchedAuthRequest;
import org.hope6537.annotation.WatchedNoAuthRequest;
import org.hope6537.dto.JoinDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.rest.utils.ResponseDict;
import org.hope6537.service.JoinService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/join/")
@EnableAutoConfiguration
public class JoinController {

    @Resource(name = "joinService")
    private JoinService joinService;

    @WatchedAuthRequest
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public Response postJoinData(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            JoinDto joinDto = dataMap.getObject("postObject", JoinDto.class);
            //判断joinDto是否合法,还需要校验其他字段
            checkNotNull(joinDto, ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = joinService.addJoin(joinDto);
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
    public Response putJoinData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            JoinDto joinDto = dataMap.getObject("putObject", JoinDto.class);
            //判断joinDto是否合法,还需要校验其他字段
            checkNotNull(joinDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(joinDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = joinService.modifyJoin(joinDto);
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
    public Response deleteJoinData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            JoinDto joinDto = dataMap.getObject("deleteObject", JoinDto.class);
            //判断joinDto是否合法,还需要校验其他字段
            checkNotNull(joinDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(joinDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = joinService.removeJoin(joinDto.getId());
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
    public Response getJoinData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            JoinDto joinDto = dataMap.getObject("join", JoinDto.class);
            //判断joinDto是否合法,还需要校验其他字段
            checkNotNull(joinDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(joinDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<JoinDto> result = joinService.getJoinById(joinDto.getId());
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
    public Response fetchJoinData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //验证完成,开始查询
            JoinDto query = PageMapUtil.getQuery(dataMap.getString("fetchObject"), dataMap.getString("pageMap"), JoinDto.class);
            ResultSupport<List<JoinDto>> joinListByQuery = joinService.getJoinListByQuery(query);
            return Response.getInstance(joinListByQuery.isSuccess())
                    .addAttribute("result", joinListByQuery.getModule())
                    .addAttribute("isEnd", joinListByQuery.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}

    