/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.basic.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.basic.dao.BasicDao;
import org.hope6537.basic.model.BasicModel;
import org.hope6537.basic.service.service.BasicService;
import org.hope6537.context.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基础控制器
 *
 * @author Hope6537
 *         Model 模型类
 *         Dao 数据持久类
 *         Service 功能业务类
 */
public abstract class BasicController<Model extends BasicModel, Dao extends BasicDao<Model>, Service extends BasicService<Model, Dao>> {

    /**
     * 基础路径，供子类调用
     */
    protected static String BASEPATH = "";
    /**
     * 获取模型泛型
     */
    @SuppressWarnings("unchecked")
    private final Class<Model> typeClass = (Class<Model>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    /**
     * 日志记录器
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 功能业务类
     */
    protected Service service;
    private Class type;

    public static void setBasepath(String basepath) {
        BASEPATH = basepath;
    }

    /**
     * 根据session获取当前登录对象
     */
    public synchronized static Object getSessionItem(HttpServletRequest request, String name) {
        return request.getSession().getAttribute(name);
    }

    public void setService(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/toPage")
    public abstract String toPage();

    /**
     * 根据模型内部信息筛选查找、要求状态正常
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected AjaxResponse getModelList() {
        logger.debug(typeClass.toString());
        Model model = Model.getInstance(typeClass);
        model.setStatus(ApplicationConstant.STATUS_NORMAL);
        List<Model> list = service.getEntryListByEntry(model);
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    /**
     * 根据模型内部信息筛选查找
     */
    @RequestMapping(method = RequestMethod.GET, value = "/model")
    @ResponseBody
    protected AjaxResponse getModelListByModel(@RequestBody Model model) {
        logger.debug(typeClass.toString());
        List<Model> list = service.getEntryListByEntry(model);
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    /**
     * 根据id获取对象，传递Json
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public AjaxResponse getSingleModel(@PathVariable String id) {
        if (ApplicationConstant.notNull(id)) {
            Model model = service.getEntryById(id);
            return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(model)).addAttribute("model", model);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERROR_CHN);
    }

    /**
     * 添加对象
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model)) {
            return AjaxResponse.getInstanceByResult(service.addEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERROR_CHN);
    }

    /**
     * 更新对象
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResponse updateModel(@RequestBody Model model) {
        //要求id不能为空
        if (ApplicationConstant.notNull(model) && ApplicationConstant.notNull(model.commonId())) {
            return AjaxResponse.getInstanceByResult(service.updateEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERROR_CHN);
    }

    /**
     * 状态无效化、将其变为事实无效化对象
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/disable")
    @ResponseBody
    public AjaxResponse disableModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model) && ApplicationConstant.notNull(model.commonId())) {
            return AjaxResponse.getInstanceByResult(service.disableEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERROR_CHN);
    }

    /**
     * 彻底从数据库中删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    public AjaxResponse deleteModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model) && ApplicationConstant.notNull(model.commonId())) {
            return AjaxResponse.getInstanceByResult(service.deleteEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERROR_CHN);
    }

}
