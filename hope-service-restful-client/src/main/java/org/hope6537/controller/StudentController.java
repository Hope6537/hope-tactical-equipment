
package org.hope6537.controller;

import com.google.common.collect.Lists;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.annotation.WatchedAuthRequest;
import org.hope6537.annotation.WatchedNoAuthRequest;
import org.hope6537.dto.StudentDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.rest.utils.ResponseDict;
import org.hope6537.service.StudentService;
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
@Controller
@RequestMapping("/student/")
@EnableAutoConfiguration
public class StudentController {

    @Resource(name = "studentService")
    private StudentService studentService;

    @WatchedAuthRequest
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public Response postStudentData(HttpServletRequest request, @RequestBody String receiveData) {
        //获取设备信息
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //--公共部分完成--
            StudentDto studentDto = dataMap.getObject("postObject", StudentDto.class);
            //判断studentDto是否合法,还需要校验其他字段
            checkNotNull(studentDto, ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = studentService.addStudent(studentDto);
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
    public Response putStudentData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            StudentDto studentDto = dataMap.getObject("putObject", StudentDto.class);
            //判断studentDto是否合法,还需要校验其他字段
            checkNotNull(studentDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(studentDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = studentService.modifyStudent(studentDto);
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
    public Response deleteStudentData(HttpServletRequest request, @RequestBody String receiveData) {
        try {
            checkNotNull(receiveData, ResponseDict.ILLEGAL_PARAM);
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            StudentDto studentDto = dataMap.getObject("deleteObject", StudentDto.class);
            //判断studentDto是否合法,还需要校验其他字段
            checkNotNull(studentDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(studentDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<Integer> operationResult = studentService.removeStudent(studentDto.getId());
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
    public Response getStudentData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            StudentDto studentDto = dataMap.getObject("student", StudentDto.class);
            //判断studentDto是否合法,还需要校验其他字段
            checkNotNull(studentDto, ResponseDict.ILLEGAL_REQUEST);
            checkNotNull(studentDto.getId(), ResponseDict.ILLEGAL_REQUEST);
            ResultSupport<StudentDto> result = studentService.getStudentById(studentDto.getId());
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
    public Response fetchStudentData(HttpServletRequest request) {
        try {
            JSONObject dataMap = (JSONObject) request.getAttribute("dataMap");
            if (dataMap == null) {
                Object errorResponse = request.getAttribute("errorResponse");
                return errorResponse != null ? (Response) errorResponse : Response.getInstance(false).setReturnMsg(ResponseDict.UNKNOWN_ERROR);
            }
            //验证完成,开始查询
            StudentDto query = PageMapUtil.getQuery(dataMap.getString("pageMap"), StudentDto.class);
            ResultSupport<List<StudentDto>> studentListByQuery = studentService.getStudentListByQuery(query);
            return Response.getInstance(studentListByQuery.isSuccess())
                    .addAttribute("result", studentListByQuery.getModule())
                    .addAttribute("isEnd", studentListByQuery.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_PARAM);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}

    