package org.hope6537.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.hope6537.annotation.WatchedAuthRequest;
import org.hope6537.annotation.WatchedNoAuthRequest;
import org.hope6537.dto.PlanDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.rest.utils.ResponseDict;
import org.hope6537.service.PlanService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 默认RESTFul API实现类
 * Created by hope6537 by Code Generator
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/plan/")
@EnableAutoConfiguration
public class PlanController {

    @Resource(name = "planService")
    private PlanService planService;

    @WatchedAuthRequest
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public Response postPlanData(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            PlanDto planDto = dataMap.getObject("postObject", PlanDto.class);
            //判断planDto是否合法,还需要校验其他字段
            checkNotNull(planDto, ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = planService.addPlan(planDto);
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @WatchedAuthRequest
    @RequestMapping(value = "post/rich", method = RequestMethod.POST)
    @ResponseBody
    public Response postPlanRichData(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            PlanDto planDto = dataMap.getObject("postObject", PlanDto.class);
            List<Integer> idList = Lists.newArrayList();
            idList.addAll(dataMap.getJSONObject("postObject").getJSONArray("classesIdList").stream().map(o -> Integer.parseInt(o.toString())).collect(Collectors.toList()));
            //判断planDto是否合法,还需要校验其他字段
            checkNotNull(planDto, ResponseDict.ILLEGAL_REQUEST);
            //查询课表信息
            PlanDto query = new PlanDto();
            query.setDay(planDto.getDay());
            ResultSupport<List<PlanDto>> planList = planService.getPlanListByQuery(query);
            checkArgument(planList.isSuccess(), "[获取作息失败]");
            final int[] successCount = {0};
            final int totalCount = idList.size();
            //修改已存在数据
            Stream<PlanDto> planDtoStream = planList.getModule().stream().filter(planDto1 -> idList.contains(planDto1.getClassesId()));
            List<Integer> putPlanIdList = planDtoStream.peek(planDto1 -> idList.remove(planDto1.getClassesId())).map(PlanDto::getId).collect(Collectors.toList());
            PlanDto commonUpdate = new PlanDto();
            commonUpdate.setData(planDto.getData());
            ResultSupport<Integer> updateResult = planService.batchModifyPlan(commonUpdate, putPlanIdList);
            successCount[0] += updateResult.isSuccess() ? updateResult.getModule() : 0;
            //添加新数据
            List<Integer> postClassesIdList = idList.stream().filter(o -> !putPlanIdList.contains(o)).collect(Collectors.toList());
            postClassesIdList.forEach(classesId -> {
                PlanDto post = new PlanDto();
                post.setData(planDto.getData());
                post.setDay(planDto.getDay());
                post.setClassesId(classesId);
                ResultSupport<Integer> integerResultSupport = planService.addPlan(post);
                successCount[0] += integerResultSupport.isSuccess() ? 1 : 0;
            });
            boolean expr = successCount[0] == totalCount;
            return Response.getInstance(expr);
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
    public Response putPlanData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            PlanDto planDto = dataMap.getObject("putObject", PlanDto.class);
            //判断planDto是否合法,还需要校验其他字段
            checkNotNull(planDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(planDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = planService.modifyPlan(planDto);
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
    public Response deletePlanData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            PlanDto planDto = dataMap.getObject("deleteObject", PlanDto.class);
            //判断planDto是否合法,还需要校验其他字段
            checkNotNull(planDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(planDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = planService.removePlan(planDto.getId());
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
    public Response getPlanData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            PlanDto planDto = dataMap.getObject("plan", PlanDto.class);
            //判断planDto是否合法,还需要校验其他字段
            checkNotNull(planDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(planDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<PlanDto> result = planService.getPlanById(planDto.getId());
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
    public Response fetchPlanData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //验证完成,开始查询
            PlanDto query = PageMapUtil.getQuery(dataMap.getString("fetchObject"), dataMap.getString("pageMap"), PlanDto.class);
            ResultSupport<List<PlanDto>> planListByQuery = planService.getPlanListByQuery(query);
            return Response.getInstance(planListByQuery.isSuccess())
                    .addAttribute("result", planListByQuery.getModule())
                    .addAttribute("isEnd", planListByQuery.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}

    