package org.hope6537.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.bo.JumpBusiness;
import org.hope6537.dto.ComicDto;
import org.hope6537.dto.JumpDto;
import org.hope6537.dto.SpecialDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.security.TokenCheckUtil;
import org.hope6537.security.AESLocker;
import org.hope6537.service.JumpService;
import org.hope6537.service.SpecialService;
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
 * Created by dintama on 16/3/24.
 */
@Controller
@RequestMapping("/special/")
@EnableAutoConfiguration
public class SpecialController {


    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "jumpBusiness")
    private JumpBusiness jumpBusiness;

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "jumpService")
    private JumpService jumpService;

    @RequestMapping(value = "add/info", method = RequestMethod.POST)
    @ResponseBody
    public Response addSpecial(HttpServletRequest request, @RequestBody String r_request) {
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
            checkNotNull(token, IILEGAL_REQUEST);
            JSONObject tokenObject = JSON.parseObject(token);
            Integer userInfoId = tokenObject.getInteger("userInfoId");
            String title = paramMap.getString("specialTitle");
            ResultSupport<Integer> integerResultSupport = specialService.addSpecial(title, userInfoId);
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
    public Response deletedSpecial(HttpServletRequest request, @RequestBody String r_request) {
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
            List<SpecialDto> specialDtos = JSONArray.parseArray(paramMap.getString("specials"), SpecialDto.class);
            List<Integer> idList = new LinkedList<>();
            for (SpecialDto o : specialDtos) {
                idList.add(o.getId());
            }
            checkArgument(!idList.isEmpty(), IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = specialService.batchRemoveSpecial(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "add/comic", method = RequestMethod.POST)
    @ResponseBody
    public Response addSpecialComic(HttpServletRequest request, @RequestBody String r_request) {
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
            SpecialDto specialDto = paramMap.getObject("special", SpecialDto.class);
            checkNotNull(specialDto, IILEGAL_REQUEST);
            ComicDto comicDto = paramMap.getObject("comic", ComicDto.class);
            checkNotNull(comicDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = jumpService.addJump(specialDto.getId(), comicDto.getId());
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted/comic", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deletedSpecialComic(HttpServletRequest request, @RequestBody String r_request) {
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
            SpecialDto specialDto = paramMap.getObject("special", SpecialDto.class);
            checkNotNull(specialDto, IILEGAL_REQUEST);
            ComicDto comicDto = paramMap.getObject("comic", ComicDto.class);
            checkNotNull(comicDto, IILEGAL_REQUEST);
            JumpDto jumpDto = new JumpDto(specialDto.getId(), comicDto.getId());
            ResultSupport<List<JumpDto>> jumpListByQuery = jumpService.getJumpListByQuery(jumpDto);
            List<Integer> idList = new LinkedList<>();
            List<JumpDto> module = jumpListByQuery.getModule();
            for (JumpDto o : module) {
                idList.add(o.getId());
            }
            ResultSupport<Integer> integerResultSupport = jumpService.batchRemoveJump(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "content", method = RequestMethod.GET)
    @ResponseBody
    public Response getSpecialContent(HttpServletRequest request, @RequestBody String r_request) {
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
            JumpDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), JumpDto.class);
            SpecialDto specialDto = paramMap.getObject("special", SpecialDto.class);
            query.setSpecialId(specialDto.getId());
            ResultSupport<JumpDto> jumpBySpecial = jumpBusiness.getJumpBySpecial(query);
            return Response.getInstance(jumpBySpecial.isSuccess())
                    .addAttribute("data", jumpBySpecial.getModule())
                    .addAttribute("isEnd", jumpBySpecial.getTotalCount() < query.getCurrentPage() * query.getPageSize())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
