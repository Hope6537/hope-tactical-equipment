package org.hope6537.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hope6537.security.TimeUtils.getUnixTime;

/**
 * Created by hope6537 on 16/3/16.
 */
public class TokenCheckUtil {

    public static final String NOT_LOGIN = "您尚未登录";
    public static final String PARSE_ERROR = "登录信息读取错误,请重新登录";
    public static final String OUT_OF_TOKEN_ID = "登录信息错误,请重新登录";
    public static final String OUT_OF_DATE = "登录信息已过期,请重新登录";
    public static final String NOT_THIS_USER = "登录信息无效,请重新登录";
    public static final String NOT_THIS_DEVICE = "登录信息设备非法,请重新登录";
    public static final String ILLEGAL_USER_ID = "非法用户信息";
    public static final String ILLEGAL_DEVICE_ID = "非法设备信息";


    public static final Integer ONE_DAY_SECONDS = 86400;

    /**
     * 检查登录Token是否合法
     * <p>
     * 0.解码
     * 1.检查token是否能被合法反序列化
     * 2.检查当前token的ID是否合法
     * 3.检查当前tokenID与UserId在Redis或者Session的关系
     * 3.检查当前token是否已经过期
     * 4.检查当前token的内含设备码和deviceId是否对应
     * </p>
     *
     * @param token    token字符串
     * @param mode     当前系统模式
     * @param deviceId 设备信息ID
     * @param request
     * @return
     */
    public static void checkLoginToken(String token, String mode, String deviceId, HttpServletRequest request) {
        checkNotNull(token, NOT_LOGIN);
        try {
            if (!"debug".equals(mode)) {
                token = AESLocker.decrypt(token);
            }
            Token tokenObject = JSON.parseObject(token, Token.class);
            checkNotNull(tokenObject, PARSE_ERROR);
            checkNotNull(tokenObject.getTokenId(), OUT_OF_TOKEN_ID);
            checkNotNull(tokenObject.getUserInfoId(), OUT_OF_TOKEN_ID);
            //检查本地Session关联
            checkArgument(request.getSession().getAttribute(tokenObject.getTokenId()).equals(tokenObject.getUserInfoId()), NOT_THIS_USER);
            //TODO:检查REDIS关联
            int availableUnixTime = Integer.parseInt(tokenObject.getAvailableUnixTime());
            checkArgument(availableUnixTime > getUnixTime(), OUT_OF_DATE);
            if (!"debug".equals(mode)) {
                checkArgument(tokenObject.deviceId.equals(deviceId), NOT_THIS_DEVICE);
            }
        } catch (JSONException e) {
            throw new RuntimeException(PARSE_ERROR);
        }
    }

    /**
     * 生成登录信息Token并保存会话
     *
     * @param userId        用户ID
     * @param availableDays 可用天数
     * @param deviceId      设备ID
     * @return
     */
    public static String initToken(String userId, Integer availableDays, String deviceId, HttpServletRequest request) {
        checkNotNull(userId, ILLEGAL_USER_ID);
        checkNotNull(deviceId, ILLEGAL_DEVICE_ID);
        if (availableDays < 1 || availableDays > 30) {
            availableDays = 7;
        }
        Token tokenInstance = Token.getTokenInstance(String.valueOf(userId), String.valueOf(TimeUtils.getUnixTime() + (availableDays * ONE_DAY_SECONDS)), deviceId);
        //创建本地Session关联
        request.getSession().setAttribute(tokenInstance.getTokenId(), tokenInstance.getUserInfoId());
        //TODO:创建REDIS关联
        return AESLocker.encrypt(JSON.toJSONString(tokenInstance));
    }

    private static class Token implements Serializable {

        /**
         * 用户ID
         */
        private String userInfoId;

        /**
         * 到该Unix时间戳过期前有效
         */
        private String availableUnixTime;

        /**
         * 设备ID
         */
        private String deviceId;

        /**
         * TokenId标识,由UUID生成
         */
        private String tokenId;


        public Token() {

        }

        private Token(String userInfoId, String availableUnixTime, String deviceId, String tokenId) {
            this.userInfoId = userInfoId;
            this.availableUnixTime = availableUnixTime;
            this.deviceId = deviceId;
            this.tokenId = tokenId;
        }

        public static Token getTokenInstance(String userInfoId, String availableUnixTime, String deviceId) {
            return new Token(userInfoId, availableUnixTime, deviceId, UUID.randomUUID().toString());
        }

        public String getUserInfoId() {
            return userInfoId;
        }

        public void setUserInfoId(String userInfoId) {
            this.userInfoId = userInfoId;
        }

        public String getAvailableUnixTime() {
            return availableUnixTime;
        }

        public void setAvailableUnixTime(String availableUnixTime) {
            this.availableUnixTime = availableUnixTime;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }
    }


}
