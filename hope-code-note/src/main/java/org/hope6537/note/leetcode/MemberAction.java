package org.hope6537.note.leetcode;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.wuyang.biz.MemberManager;
import com.alibaba.wuyang.dataobject.Member;
import org.hope6537.ajax.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by wuyang.zp on 2015/5/7.
 */
public class MemberAction {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ParserRequestContext parserRequestContext;

    private PrintWriter out;

    private static String toResult(Object value, String paramName) {
        AjaxResponse ajaxResponse = AjaxResponse.getInstanceByResult(value != null).addAttribute(paramName, value);
        return JSON.toJSONString(ajaxResponse);
    }

    public void doMember(Context context) throws IOException {
        final String method = request.getMethod();
        ParameterParser parameters = parserRequestContext.getParameters();
        Member member = parameters.getObjectOfType("member", Member.class);
        String memberId = parameters.getString("memberId");
        String memberEmail = parameters.getString("memberEmail");
        out = response.getWriter();
        response.setContentType("application/json");
        String result = null;
        switch (method) {
            case "GET":
                //TODO:RESTFul
                if (member != null) {
                    List<Member> memberList = memberManager.getMemberList(member);
                    result = toResult(memberList, "list");
                } else if (memberId != null) {
                    Member queryMember = memberManager.getMemberById(memberId);
                    result = toResult(queryMember, "member");
                } else if (memberEmail != null) {
                    Member queryMember = memberManager.getMemberByEmail(memberEmail);
                    result = toResult(queryMember, "member");
                }
                break;
            case "POST":
                if (member != null) {
                    memberManager.register(member);
                    result = JSON.toJSONString(AjaxResponse.getInstanceByResult(true));
                }
                break;
            case "PUT":
                if (member != null) {
                    memberManager.update(member);
                    result = JSON.toJSONString(AjaxResponse.getInstanceByResult(true));
                }
                break;
            case "DELETE":
                if (memberId != null) {
                    int res = memberManager.disable(memberId);
                    result = JSON.toJSONString(AjaxResponse.getInstanceByResult(res != -1).addAttribute("res", res));
                }
                break;
            default:
                response.setStatus(400);
                return;
        }
        out.print(result);
        out.flush();

    }


}
