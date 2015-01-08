package org.hope6537.wonderland.sail.ajax.response;

import org.hope6537.wonderland.sail.mybatis.model.Page;

import java.util.List;

/**
 * @author gaoxinyu
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

