package org.hope6537.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.bo.CategoryBusiness;
import org.hope6537.dto.CategoryDto;
import org.hope6537.dto.ClassifiedDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.security.TokenCheckUtil;
import org.hope6537.security.AESLocker;
import org.hope6537.service.CategoryService;
import org.hope6537.service.ClassifiedService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dintama on 16/3/23.
 */
@Controller
@RequestMapping("/classification/")
@EnableAutoConfiguration
public class ClassifiedController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "classifiedService")
    private ClassifiedService classifiedService;

    @Resource(name = "categoryBusiness")
    private CategoryBusiness categoryBusiness;

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @RequestMapping(value = "category/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addCategory(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            CategoryDto categoryDto = paramMap.getObject("category", CategoryDto.class);
            checkNotNull(categoryDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = categoryService.addCategory(categoryDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "category/deleted", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deletedCategory(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            List<CategoryDto> module = JSONArray.parseArray(paramMap.getString("categorys"), CategoryDto.class);
            checkNotNull(module, IILEGAL_REQUEST);
            checkArgument(!module.isEmpty(), IILEGAL_REQUEST);
            List<Integer> idList = new LinkedList<>();
            for (CategoryDto o : module) {
                idList.add(o.getId());
            }
            ResultSupport<Integer> integerResultSupport = categoryService.batchRemoveCategory(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Response addClassified(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            checkArgument(classifiedDto.getCoverTitle() != null && classifiedDto.getTitle() != null, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.addClassified(classifiedDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deletedClassified(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.removeClassified(classifiedDto.getId());
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted/batch", method = RequestMethod.DELETE)
    @ResponseBody
    public Response batchDeletedClassified(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            List<ClassifiedDto> module = JSONArray.parseArray(paramMap.getString("classifieds"), ClassifiedDto.class);
            checkNotNull(module, IILEGAL_REQUEST);
            checkArgument(!module.isEmpty(), IILEGAL_REQUEST);
            List<Integer> idList = new LinkedList<>();
            for (ClassifiedDto o : module) {
                idList.add(o.getId());
            }
            ResultSupport<Integer> integerResultSupport = classifiedService.batchRemoveClassified(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "updated", method = RequestMethod.PUT)
    @ResponseBody
    public Response updatedClassified(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.modifyClassified(classifiedDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedIndex(HttpServletRequest request) {
        //获取参数
        String data = request.getParameter("data");
        JSONObject paramMap;
        String mode = request.getParameter("_mode");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            ClassifiedDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), ClassifiedDto.class);
            ResultSupport<List<ClassifiedDto>> classifiedListByQuery = classifiedService.getClassifiedListByQuery(query);
            return Response.getInstance(classifiedListByQuery.isSuccess())
                    .addAttribute("data", classifiedListByQuery.getModule())
                    .addAttribute("isEnd", classifiedListByQuery.getTotalCount() < query.getCurrentPage() * query.getPageSize())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }


    @RequestMapping(value = "comic/detail", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedComicDetail(HttpServletRequest request) {
        //获取参数
        String data = request.getParameter("data");
        JSONObject paramMap;
        String mode = request.getParameter("_mode");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            CategoryDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), CategoryDto.class);
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            query.setClassifiedId(classifiedDto.getId());
            ResultSupport<CategoryDto> comicByClassified = categoryBusiness.getComicByClassified(query);
            return Response.getInstance(comicByClassified.isSuccess())
                    .addAttribute("data", comicByClassified.getModule())
                    .addAttribute("isEnd", comicByClassified.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special/detail", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedSpecialDetail(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            CategoryDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), CategoryDto.class);
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            query.setClassifiedId(classifiedDto.getId());
            ResultSupport<CategoryDto> specialByClassified = categoryBusiness.getSpecialByClassified(query);
            return Response.getInstance(specialByClassified.isSuccess())
                    .addAttribute("data", specialByClassified.getModule())
                    .addAttribute("isEnd", specialByClassified.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "volatile", method = RequestMethod.GET)
    @ResponseBody
    public Response volatileClassified(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            ClassifiedDto classified = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classified.getTitle(), IILEGAL_REQUEST);
            ResultSupport<List<ClassifiedDto>> classifiedListByQuery = classifiedService.getClassifiedListByQuery(classified);
            if (classifiedListByQuery.getModule().size() != 0) {
                return Response.getInstance(false).setReturnMsg("该分类名称已被使用");
            }
            return Response.getInstance(classifiedListByQuery.isSuccess())
                    .addAttribute("data", classifiedListByQuery.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


}
