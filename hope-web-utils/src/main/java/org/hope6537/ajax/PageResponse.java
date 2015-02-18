package org.hope6537.ajax;

import org.hope6537.page.model.Page;

import java.util.List;

/**
 * 用于返回分页信息
 */
public class PageResponse<T> {
    private Page page;
    private List<T> resultList;

    public PageResponse() {
    }

    public PageResponse(Page page, List<T> resultList) {
        this.page = page;
        this.resultList = resultList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}

