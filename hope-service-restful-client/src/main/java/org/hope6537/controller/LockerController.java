package org.hope6537.controller;

import org.hope6537.security.AESLocker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(maxAge = 3600)
@Controller
@EnableAutoConfiguration
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
public class LockerController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    @ResponseBody
    public String getEncryptMessage(HttpServletRequest request) {
        String message = request.getParameter("message");
        return AESLocker.encrypt(message);
    }

    @RequestMapping(value = "/encrypt64", method = RequestMethod.GET)
    @ResponseBody
    public String getEncrypt64Message(HttpServletRequest request) {
        String message = request.getParameter("message");
        return AESLocker.encryptBase64(message);
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.GET)
    @ResponseBody
    public String getDecryptMessage(HttpServletRequest request) {
        String message = request.getParameter("message");
        return AESLocker.decrypt(message);
    }

    @RequestMapping(value = "/decrypt64", method = RequestMethod.GET)
    @ResponseBody
    public String getDecrypt64Message(HttpServletRequest request) {
        String message = request.getParameter("message");
        return AESLocker.decryptBase64(message);
    }

}