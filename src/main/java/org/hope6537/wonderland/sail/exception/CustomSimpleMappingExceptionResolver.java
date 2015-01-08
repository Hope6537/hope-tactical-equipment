package org.hope6537.wonderland.sail.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hope6537.wonderland.sail.ajax.response.AjaxResponse;
import org.hope6537.wonderland.sail.ajax.response.ReturnState;
import org.hope6537.wonderland.sail.constant.Constant;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CustomSimpleMappingExceptionResolver
 *
 * @author gaoxinyu
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        String viewName = determineViewName(ex, request);
        if (viewName == null) {
            return null;
        }
        if (isJsonReturn(request)) {
            try {// JSON格式返回
                PrintWriter writer = response.getWriter();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                String errorMsg;
                if (ex instanceof ValidateErrorException) {
                    errorMsg = Constant.OPERATE_ERROR + ex.getMessage();
                } else if (ex instanceof UnauthorizedException) {
                    errorMsg = Constant.OPERATE_ERROR + "权限不足！" + ex.getMessage();
                } else {
                    logger.error(Constant.ERROR, ex);
                    errorMsg = Constant.OPERATE_ERROR + "  " + ex;
                }
                writer.write(new ObjectMapper().writeValueAsString(new AjaxResponse(ReturnState.ERROR, errorMsg)));
                writer.flush();
            } catch (IOException e) {
                logger.error(Constant.ERROR, ex);
            }
            return null;
        } else {// JSP格式返回
            logger.error(Constant.ERROR, ex);
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            return getModelAndView(viewName, ex, request);
        }
    }

    private boolean isJsonReturn(HttpServletRequest request) {
        if (request.getHeader("accept") == null) {
            return false;
        }
        if (request.getHeader("accept").contains("application/json")) {
            return true;
        }
        String xRequestedWith = request.getHeader("X-Requested-With");
        return xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
    }
}
