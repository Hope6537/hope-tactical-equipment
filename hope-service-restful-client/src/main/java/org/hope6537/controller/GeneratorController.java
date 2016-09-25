package org.hope6537.controller;

import org.hope6537.business.GeneratorBusiness;
import org.hope6537.entity.Response;
import org.hope6537.entity.ResultSupport;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 生成器Controller
 * Created by hope6537 on 16/5/20.
 */
@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/generator/")
@EnableAutoConfiguration
public class GeneratorController {


    @Resource(name = "generatorBusiness")
    private GeneratorBusiness generatorBusiness;

    @RequestMapping(value = "parent", method = RequestMethod.GET)
    @ResponseBody
    public Response generateParents(HttpServletRequest request) {
        String count = request.getParameter("count");
        if (count == null) {
            count = "20";
        }
        try {
            ResultSupport<List<Integer>> operationResult = generatorBusiness.generateParents(Integer.parseInt(count));
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "student", method = RequestMethod.GET)
    @ResponseBody
    public Response generateStudents(HttpServletRequest request) {
        String count = request.getParameter("count");
        if (count == null) {
            count = "20";
        }
        try {
            ResultSupport<List<Integer>> operationResult = generatorBusiness.generateStudents(Integer.parseInt(count));
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "teacher", method = RequestMethod.GET)
    @ResponseBody
    public Response generateTeachers(HttpServletRequest request) {
        String count = request.getParameter("count");
        if (count == null) {
            count = "10";
        }
        try {
            ResultSupport<List<Integer>> operationResult = generatorBusiness.generateTeachers(Integer.parseInt(count));
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "classes", method = RequestMethod.GET)
    @ResponseBody
    public Response generateClasses(HttpServletRequest request) {
        String count = request.getParameter("count");
        if (count == null) {
            count = "5";
        }
        try {
            ResultSupport<List<Integer>> operationResult = generatorBusiness.generateClasses(Integer.parseInt(count));
            return Response.getInstance(operationResult.isSuccess()).addAttribute("result", operationResult.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    /**
     * 授课查询
     * select d.teacherId,count(d.teacherId) from Duty d,Teacher t where t.id = d.teacherId group by d.teacherId;
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "classes_teacher", method = RequestMethod.GET)
    @ResponseBody
    public Response generateClassesTeacherRelation(HttpServletRequest request) {
        String count = request.getParameter("count");
        if (count == null) {
            count = "10";
        }
        try {
            ResultSupport<Boolean> result = generatorBusiness.generateClassesTeacherRelation(Integer.parseInt(count));
            return Response.getInstance(result.isSuccess()).addAttribute("result", result.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    /**
     * 分班查询
     * select classesId,count(classesId) from Student group by classesId;
     *
     * @return
     */
    @RequestMapping(value = "classes_student", method = RequestMethod.GET)
    @ResponseBody
    public Response generateClassesStudentRelation() {
        try {
            ResultSupport<Boolean> result = generatorBusiness.generateClassesStudentRelation();
            return Response.getInstance(result.isSuccess()).addAttribute("result", result.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    /**
     * 分配查询
     * select p.name as parent,s.parentId,count(s.parentId) from Student s,Parent p where p.id = s.parentId group by s.parentId;
     *
     * @return
     */
    @RequestMapping(value = "parent_student", method = RequestMethod.GET)
    @ResponseBody
    public Response generateParentStudentRelation() {
        try {
            ResultSupport<Boolean> result = generatorBusiness.generateParentStudentRelation();
            return Response.getInstance(result.isSuccess()).addAttribute("result", result.getModule());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


}
