package org.hope6537.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.hope6537.annotation.WatchedAuthRequest;
import org.hope6537.annotation.WatchedNoAuthRequest;
import org.hope6537.dto.PublishDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.rest.utils.ResponseDict;
import org.hope6537.service.PublishService;
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
@RequestMapping("/publish/")
@EnableAutoConfiguration
public class PublishController {

    @Resource(name = "publishService")
    private PublishService publishService;

    @WatchedAuthRequest
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public Response postPublishData(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            PublishDto publishDto = dataMap.getObject("postObject", PublishDto.class);
            //判断publishDto是否合法,还需要校验其他字段
            checkNotNull(publishDto, ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = publishService.addPublish(publishDto);
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @WatchedAuthRequest
    @RequestMapping(value = "batchPost", method = RequestMethod.POST)
    @ResponseBody
    public Response batchPostPublishData(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            JSONObject postObject = dataMap.getJSONObject("postObject");
            Integer eventId = postObject.getInteger("eventId");
            JSONArray classesIdList = postObject.getJSONArray("classesIdList");

            int serviceSuccess = 0;
            for (Object o : classesIdList) {
                Integer classesId = Integer.parseInt(o.toString());
                PublishDto publishDto = new PublishDto(eventId, classesId);
                checkNotNull(publishDto, ResponseDict.ILLEGAL_REQUEST);
                ResultSupport<Integer> operationResult = publishService.addPublish(publishDto);
                if (operationResult.isSuccess()) {
                    serviceSuccess++;
                }
            }
            boolean expr = classesIdList.size() == serviceSuccess;
            return Response.getInstance(expr).setReturnMsg(expr ? "活动关联成功" : "活动关联失败");
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
    public Response putPublishData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            PublishDto publishDto = dataMap.getObject("putObject", PublishDto.class);
            //判断publishDto是否合法,还需要校验其他字段
            checkNotNull(publishDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(publishDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = publishService.modifyPublish(publishDto);
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
    public Response deletePublishData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            PublishDto publishDto = dataMap.getObject("deleteObject", PublishDto.class);
            //判断publishDto是否合法,还需要校验其他字段
            checkNotNull(publishDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(publishDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = publishService.removePublish(publishDto.getId());
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
    public Response getPublishData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            PublishDto publishDto = dataMap.getObject("publish", PublishDto.class);
            //判断publishDto是否合法,还需要校验其他字段
            checkNotNull(publishDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(publishDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<PublishDto> result = publishService.getPublishById(publishDto.getId());
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
    public Response fetchPublishData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //验证完成,开始查询
            PublishDto query = PageMapUtil.getQuery(dataMap.getString("fetchObject"), dataMap.getString("pageMap"), PublishDto.class);
            ResultSupport<List<PublishDto>> publishListByQuery = publishService.getPublishListByQuery(query);
            return Response.getInstance(publishListByQuery.isSuccess())
                    .addAttribute("result", publishListByQuery.getModule())
                    .addAttribute("isEnd", publishListByQuery.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @WatchedNoAuthRequest
    @RequestMapping(value = "fetch/classesIdList", method = RequestMethod.GET)
    @ResponseBody
    public Response fetchEventClassesIdList(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //验证完成,开始查询
            PublishDto query = PageMapUtil.getQuery(dataMap.getString("fetchObject"), dataMap.getString("pageMap"), PublishDto.class);
            ResultSupport<List<PublishDto>> publishListByQuery = publishService.getPublishListByQuery(query);
            List<Integer> classesIdList = Lists.newArrayList();
            publishListByQuery.getModule().stream().forEach(publishDto -> classesIdList.add(publishDto.getClassesId()));
            return Response.getInstance(!classesIdList.isEmpty()).addAttribute("result", classesIdList);
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


}

    