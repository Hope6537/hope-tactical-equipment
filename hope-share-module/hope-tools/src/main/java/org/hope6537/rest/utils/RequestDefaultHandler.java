package org.hope6537.rest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hope6537.annotation.WatchedAuthRequest;
import org.hope6537.annotation.WatchedNoAuthRequest;
import org.hope6537.entity.Response;
import org.hope6537.exception.DecryptException;
import org.hope6537.security.AESLocker;
import org.hope6537.security.TokenCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * RESTFul API请求处理与校验切面
 * Created by hope6537 on 16/5/15.
 */
@Aspect
public class RequestDefaultHandler {

    private final Logger logger = LoggerFactory.getLogger("monitor");

    @Pointcut("@annotation(org.hope6537.annotation.WatchedNoAuthRequest)")
    private void annotationNoAuthMethods() {

    }

    @Pointcut("@annotation(org.hope6537.annotation.WatchedAuthRequest)")
    private void annotationAuthMethods() {

    }

    @Around("annotationAuthMethods() || annotationNoAuthMethods()")
    public Object authInterceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String executionId = UUID.randomUUID().toString();
        Signature signature = proceedingJoinPoint.getSignature();
        Object[] methodArgs;
        if (signature instanceof MethodSignature) {
            RequestFacade requestFacade = null;
            try {
                Method method = ((MethodSignature) signature).getMethod();
                WatchedAuthRequest auth = method.getDeclaredAnnotation(WatchedAuthRequest.class);
                WatchedNoAuthRequest noAuth = method.getDeclaredAnnotation(WatchedNoAuthRequest.class);
                if (auth != null && noAuth != null) {
                    throw new RuntimeException("不能同时添加WatchedAuthRequest和WatchedNoAuthRequest注解");
                }
                boolean withAuth = auth != null;
                methodArgs = proceedingJoinPoint.getArgs();
                logger.debug("[请求预处理][方法执行ID:" + executionId + "][识别参数中]");
                if (proceedingJoinPoint.getArgs()[0] instanceof RequestFacade) {
                    logger.debug("[请求预处理][方法执行ID:" + executionId + "][参数第一项为HttpServletRequest][开始校验请求类别]");
                    requestFacade = (RequestFacade) methodArgs[0];
                    switch (requestFacade.getMethod()) {
                        case "GET":
                            logger.debug("[请求预处理][方法执行ID:" + executionId + "][请求类别为GET]");
                            handleUrlParameterRequest(executionId, requestFacade, withAuth);
                            break;
                        case "POST":
                        case "PUT":
                        case "DELETE":
                            logger.debug("[请求预处理][方法执行ID:" + executionId + "][请求类别为" + requestFacade.getMethod() + "]");
                            if (methodArgs[1] == null || !(methodArgs[1] instanceof String)) {
                                logger.error("[请求预处理][方法执行ID:" + executionId + "][请求第二个参数不是字符串或合法的JSON字符串]");
                                requestFacade.setAttribute("dataMap", null);
                                requestFacade.setAttribute("error", null);
                                break;
                            }
                            handleBodyRequest(executionId, requestFacade, (String) methodArgs[1], withAuth);
                            break;
                        default:
                            logger.error("[请求预处理][方法执行ID:" + executionId + "][未知的请求方式,禁止]");
                            requestFacade.setAttribute("dataMap", null);
                    }
                }
                return proceedingJoinPoint.proceed(methodArgs);
            } catch (Exception e) {
                logger.error("[请求预处理][方法执行ID:" + executionId + "][出现异常][异常信息][" + e.getMessage() + "]");
                e.printStackTrace();
                throw e;
            }
        } else {
            throw new Exception("[切入点不是方法切入点]");
        }
    }

    public void handleUrlParameterRequest(String executionId, RequestFacade requestFacade, boolean withAuth) {
        String data = requestFacade.getParameter("data");
        String mode = requestFacade.getParameter("_mode");
        String auth = requestFacade.getParameter("_auth");
        commonHandleLogic(executionId, requestFacade, withAuth, data, mode, auth);
    }

    public void handleBodyRequest(String executionId, RequestFacade requestFacade, String receiveData, boolean withAuth) {
        JSONObject requestData = JSON.parseObject(receiveData);
        String data = requestData.getString("data");
        String mode = requestData.getString("_mode");
        String auth = requestData.getString("_auth");
        commonHandleLogic(executionId, requestFacade, withAuth, data, mode, auth);
    }

    public void commonHandleLogic(String executionId, RequestFacade requestFacade, boolean withAuth, String data, String mode, String auth) {
        try {
            logger.debug("[请求预处理][方法执行ID:" + executionId + "][data参数为][" + data + "][mode参数为][" + mode + "][auth参数为][" + auth + "]");
            //判断data是否合法
            checkNotNull(data, ResponseDict.ILLEGAL_REQUEST);
            checkArgument(!data.isEmpty(), ResponseDict.ILLEGAL_REQUEST);
            //判断是否需要对data进行解密
            data = !"debug".equals(mode) ? AESLocker.decryptBase64(data) : data;
            checkNotNull(data, ResponseDict.ILLEGAL_ENTRYPT_MESSAGE);
            //获取验证登录的必要信息
            JSONObject dataMap = JSON.parseObject(data);
            if (withAuth) {
                String token = dataMap.getString("token");
                String deviceId = dataMap.getString("deviceId");
                logger.debug("[请求预处理][正在执行方法访问校验][token][" + token + "][deviceId][" + deviceId + "]");
                //token验证
                if (!"debug".equals(auth)) {
                    TokenCheckUtil.checkLoginToken(token, mode, deviceId, requestFacade);
                }
            }
            logger.debug("[请求预处理][方法执行ID:" + executionId + "][完成请求预处理]");
            requestFacade.setAttribute("dataMap", dataMap);
        } catch (DecryptException de) {
            logger.error("[请求预处理][方法执行ID:" + executionId + "][出现异常][解密失败]");
            requestFacade.setAttribute("dataMap", null);
            requestFacade.setAttribute("errorResponse", Response.getInstance(false).setReturnMsg(ResponseDict.ILLEGAL_ENTRYPT_MESSAGE));
            de.printStackTrace();
        } catch (Exception e) {
            logger.error("[请求预处理][方法执行ID:" + executionId + "][出现异常][异常信息][" + e.getMessage() + "]");
            requestFacade.setAttribute("dataMap", null);
            requestFacade.setAttribute("errorResponse", Response.getInstance(false).setReturnMsg(e.getMessage()));
            e.printStackTrace();
        }
    }

}
