package org.hope6537.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.hope6537.bo.FavoriteBusiness;
import org.hope6537.dto.ComicDto;
import org.hope6537.dto.FavoriteDto;
import org.hope6537.dto.SpecialDto;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.hope6537.page.PageMapUtil;
import org.hope6537.security.TokenCheckUtil;
import org.hope6537.security.AESLocker;
import org.hope6537.service.FavoriteService;
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
@RequestMapping("/mine/")
@EnableAutoConfiguration
public class MineController {

    private static final int TYPE_COMIC = 0;
    private static final int TYPE_SPECIAL = 1;
    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "favoriteBusiness")
    private FavoriteBusiness favoriteBusiness;

    @Resource(name = "favoriteService")
    private FavoriteService favoriteService;

    private List<Integer> getFavoriteIdList(Integer userInfoId, Integer targetId, Integer targetType) {
        FavoriteDto favoriteDto = new FavoriteDto(userInfoId, targetId, targetType);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(favoriteDto);
        checkNotNull(favoriteListByQuery.getModule(), IILEGAL_REQUEST);
        checkArgument(!favoriteListByQuery.getModule().isEmpty(), IILEGAL_REQUEST);
        List<FavoriteDto> favoriteDtoList = favoriteListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for (FavoriteDto o : favoriteDtoList) {
            idList.add(o.getTargetId());
        }
        return idList;
    }


    @RequestMapping(value = "comic/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addMineCollection(HttpServletRequest request, @RequestBody String r_request) {
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
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            ComicDto comicDto = paramMap.getObject("comic", ComicDto.class);
            checkNotNull(comicDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(userInfoId, comicDto.getId(), TYPE_COMIC);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "comic/deleted", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deletedMineCollection(HttpServletRequest request, @RequestBody String r_request) {
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
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            ComicDto comicDto = paramMap.getObject("comic", ComicDto.class);
            List<Integer> favoriteIdList = getFavoriteIdList(userInfoId, comicDto.getId(), TYPE_COMIC);
            ResultSupport<Integer> integerResultSupport = favoriteService.batchRemoveFavorite(favoriteIdList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "comic/index", method = RequestMethod.GET)
    @ResponseBody
    public Response getMineCollection(HttpServletRequest request, @RequestBody String r_request) {
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
            FavoriteDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), FavoriteDto.class);
            JSONObject tokenObject = JSON.parseObject(token);
            Integer userInfoId = tokenObject.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            query.setUserId(userInfoId);
            ResultSupport<FavoriteDto> userFavoriteComics = favoriteBusiness.getUserFavoriteComics(query);
            return Response.getInstance(userFavoriteComics.isSuccess())
                    .addAttribute("data", userFavoriteComics.getModule())
                    .addAttribute("isEnd", userFavoriteComics.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special/add", method = RequestMethod.POST)
    @ResponseBody
    public Response addMineSpecial(HttpServletRequest request, @RequestBody String r_request) {
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
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            SpecialDto specialDto = paramMap.getObject("special", SpecialDto.class);
            ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(userInfoId, specialDto.getId(), TYPE_SPECIAL);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special/deleted", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deletedMineSpecial(HttpServletRequest request, @RequestBody String r_request) {
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
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            SpecialDto specialDto = paramMap.getObject("special", SpecialDto.class);
            List<Integer> favoriteIdList = getFavoriteIdList(userInfoId, specialDto.getId(), TYPE_SPECIAL);
            ResultSupport<Integer> integerResultSupport = favoriteService.batchRemoveFavorite(favoriteIdList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special/index", method = RequestMethod.GET)
    @ResponseBody
    public Response getMineSpecial(HttpServletRequest request, @RequestBody String r_request) {
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
            FavoriteDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), FavoriteDto.class);
            JSONObject tokenObject = JSON.parseObject(token);
            Integer userInfoId = tokenObject.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            query.setUserId(userInfoId);
            ResultSupport<FavoriteDto> userFavoriteSpecials = favoriteBusiness.getUserFavoriteSpecials(query);
            return Response.getInstance(userFavoriteSpecials.isSuccess())
                    .addAttribute("data", userFavoriteSpecials.getModule())
                    .addAttribute("isEnd", userFavoriteSpecials.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
