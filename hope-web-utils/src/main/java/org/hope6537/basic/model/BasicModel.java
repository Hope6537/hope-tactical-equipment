/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.basic.model;


import org.hope6537.context.ApplicationConstant;

import java.io.Serializable;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public abstract class BasicModel implements Serializable {

    private static final long serialVersionUID = 9162306082675078600L;

    protected Integer start;

    protected Integer pageLength;

    protected Integer thisIndex;

    protected String status;

    public static <T extends BasicModel> T getInstance(Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract String commonId();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageLength() {
        return pageLength;
    }

    public void setPageLength(Integer pageLength) {
        this.pageLength = pageLength;
    }

    public Integer getThisIndex() {
        return thisIndex;
    }

    public void setThisIndex(Integer thisIndex) {
        this.thisIndex = thisIndex;
    }

    public final void disable() {
        this.setStatus(ApplicationConstant.STATUS_DIE);
    }

    public final void enable() {
        this.setStatus(ApplicationConstant.STATUS_NORMAL);
    }

}
