package org.hope6537.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.dto.UserInfoDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.security.TokenCheckUtil;
import org.hope6537.security.AESLocker;
import org.hope6537.service.UserInfoService;
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
 * Created by dintama on 16/3/31.
 */
@Controller
@RequestMapping("/user/")
@EnableAutoConfiguration
public class UserInfoController {

    private static final String IILEGAL_REQUEST = "非法请求";
    private static final Integer DEFALUT_AVAILABLE_DAYS = 7;

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserInfoById(HttpServletRequest request, @RequestBody String r_request) {
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
            checkNotNull(token, IILEGAL_REQUEST);
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            ResultSupport<UserInfoDto> userInfoById = userInfoService.getUserInfoById(userInfoId);
            return Response.getInstance(userInfoById.isSuccess())
                    .addAttribute("data", userInfoById.getModule());
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg("非法参数");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }


    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Response signUpUserInfo(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        String result = "";
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        //获取设备信息
        String mode = JSONRequest.getString("_mode");
        try {
            //判断data是否合法
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            //判断是否需要对data进行加密
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            //获取验证登录的必要信息
            paramMap = JSON.parseObject(data);
            String deviceId = paramMap.getString("deviceId");
            UserInfoDto userInfoDto = paramMap.getObject("userInfo", UserInfoDto.class);
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            checkNotNull(userInfoDto.getUsername(), IILEGAL_REQUEST);
            checkNotNull(userInfoDto.getPassword(), IILEGAL_REQUEST);
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            //验证完成 -- username和nickname应该在焦点离开时异步校验的吧= =
            ResultSupport<Integer> integerResultSupport = userInfoService.addUserInfo(userInfoDto);
            if (integerResultSupport.isSuccess()) {
                //登录成功
                result = TokenCheckUtil.initToken(integerResultSupport.getModule().toString(), DEFALUT_AVAILABLE_DAYS, deviceId, request);
            }
            return Response.getInstance(integerResultSupport.isSuccess())       //这个分页什么的应该不用加到里面了吧..
                    .addAttribute("token", result);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "updated", method = RequestMethod.PUT)
    @ResponseBody
    public Response updatedUserInfo(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        try {
            //判断data是否合法
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            UserInfoDto userInfoDto = paramMap.getObject("userInfo", UserInfoDto.class);
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            //验证完成 -- nickname在焦点离开时异步校验
            ResultSupport<Integer> integerResultSupport = userInfoService.modifyUserInfo(userInfoDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "deleted", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deletedUserInfo(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            UserInfoDto userInfoDto = paramMap.getObject("userInfo", UserInfoDto.class);
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = userInfoService.removeUserInfo(userInfoDto.getId());
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted/batch", method = RequestMethod.DELETE)
    @ResponseBody
    public Response batchDeletedUserInfo(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkNotNull(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);

            List<UserInfoDto> module = JSONArray.parseArray(paramMap.getString("userInfos"), UserInfoDto.class);
            checkNotNull(module, IILEGAL_REQUEST);
            checkArgument(!module.isEmpty(), IILEGAL_REQUEST);
            List<Integer> idList = new LinkedList<>();
            for (UserInfoDto o : module) {
                idList.add(o.getId());
            }
            ResultSupport<Integer> integerResultSupport = userInfoService.batchRemoveUserInfo(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    public Response signInUserInfo(HttpServletRequest request) {
        //获取参数
        String data = request.getParameter("data");
        com.alibaba.fastjson.JSONObject paramMap;
        //获取设备信息
        String mode = request.getParameter("_mode");
        try {
            //判断data是否合法
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            //判断是否需要对data进行加密
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            //获取登录后的必要信息
            paramMap = JSON.parseObject(data);
            String deviceId = paramMap.getString("deviceId");
            UserInfoDto userInfoDto = paramMap.getObject("userInfo", UserInfoDto.class);
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            checkNotNull(userInfoDto.getUsername(), IILEGAL_REQUEST);
            checkNotNull(userInfoDto.getPassword(), IILEGAL_REQUEST);
            //验证完成,开始查询
            ResultSupport<List<UserInfoDto>> userInfoListByQuery = userInfoService.getUserInfoListByQuery(userInfoDto);
            if (userInfoListByQuery.getModule().size() != 1) {        //没匹配到,登录失败~
                return Response.getInstance(false).setReturnMsg("登录失败,请检查用户名密码是否正确");
            }
            UserInfoDto user = userInfoListByQuery.getModule().get(0);
            //登录成功
            String result = TokenCheckUtil.initToken(user.getId().toString(), DEFALUT_AVAILABLE_DAYS, deviceId, request);
            return Response.getInstance(userInfoListByQuery.isSuccess())
                    .addAttribute("token", result);
        } catch (JSONException e) {
            return Response.getInstance(false).setReturnMsg("非法参数");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    /**
     * 用户注册前验证username
     * 用户修改或注册时nickname的验证
     */
    @RequestMapping(value = "volatile", method = RequestMethod.GET)
    @ResponseBody
    public Response volatileUser(HttpServletRequest request, @RequestBody String r_request) {
        JSONObject JSONRequest = JSON.parseObject(r_request);
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        String mode = JSONRequest.getString("_mode");
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            UserInfoDto userInfoDto = paramMap.getObject("userInfo", UserInfoDto.class);
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            checkArgument(userInfoDto.getNickname() != null || userInfoDto.getUsername() != null, IILEGAL_REQUEST);
            //验证完成,开始查询
            ResultSupport<List<UserInfoDto>> userInfoListByQuery = userInfoService.getUserInfoListByQuery(userInfoDto);
            Integer flag = userInfoListByQuery.getModule().size();
            if (flag != 0) {   //该账号已经被注册
                return Response.getInstance(false)
                        .setReturnMsg("[该用户名或昵称已经被占用]");
            }
            return Response.getInstance(userInfoListByQuery.isSuccess())        //可能查询失败
                    .addAttribute("data", flag);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
