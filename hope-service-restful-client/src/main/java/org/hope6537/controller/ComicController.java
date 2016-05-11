package org.hope6537.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.bo.CategoryBusiness;
import org.hope6537.dto.CategoryDto;
import org.hope6537.dto.ComicDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.security.TokenCheckUtil;
import org.hope6537.security.AESLocker;
import org.hope6537.service.ComicService;
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

@Controller
@RequestMapping("/comic/")
@EnableAutoConfiguration
public class ComicController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "categoryBusiness")
    private CategoryBusiness categoryBusiness;


    @RequestMapping(value = "add/info", method = RequestMethod.POST)
    @ResponseBody
    public Response addComicInfo(HttpServletRequest request, @RequestBody String r_request) {
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
            ComicDto comic = paramMap.getObject("comic", ComicDto.class);
            checkNotNull(comic, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = comicService.addComic(comic);
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
    public Response deletedComic(HttpServletRequest request, @RequestBody String r_request) {
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
            List<ComicDto> module = JSONArray.parseArray(paramMap.getString("comics"), ComicDto.class);
            checkNotNull(module, IILEGAL_REQUEST);
            checkArgument(!module.isEmpty(), IILEGAL_REQUEST);
            List<Integer> idList = new LinkedList<>();
            for (ComicDto o : module) {
                idList.add(o.getId());
            }
            ResultSupport<Integer> integerResultSupport = comicService.batchRemoveComic(idList);
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
    public Response updatedComic(HttpServletRequest request, @RequestBody String r_request) {
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
            ComicDto comicDto = paramMap.getObject("comic", ComicDto.class);
            checkNotNull(comicDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = comicService.modifyComic(comicDto);
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
    public Response getComicInfoByComicId(HttpServletRequest request) {
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
            ComicDto comicDto = paramMap.getObject("comic", ComicDto.class);
            CategoryDto categoryDto = new CategoryDto();
            //因为Business中只需要comicId
            categoryDto.setTargetId(comicDto.getId());
            ResultSupport<CategoryDto> comicClassified = categoryBusiness.getComicClassified(categoryDto);
            return Response.getInstance(comicClassified.isSuccess())
                    .addAttribute("category", comicClassified.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "getComic", method = RequestMethod.GET)
    @ResponseBody
    public Response getComicByQuery(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        //获取设备信息
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        boolean isEnd = false;
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
            ComicDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), ComicDto.class);
            ResultSupport<List<ComicDto>> comicListByQuery = comicService.getComicListByQuery(query);
            return Response.getInstance(comicListByQuery.isSuccess())
                    .addAttribute("data", comicListByQuery.getModule())
                    .addAttribute("isEnd", comicListByQuery.getTotalCount() < query.getCurrentPage() * query.getPageSize())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


}