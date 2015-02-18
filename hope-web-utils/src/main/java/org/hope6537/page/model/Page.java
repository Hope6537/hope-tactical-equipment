package org.hope6537.page.model;

import java.util.Map;

/**
 * 分页对象模型类
 */

public class Page {
    private final static int PAGE_SIZE = 30;
    private final static int FIRST_PAGE = 1;
    private final static int MAX_SUM_LIMIT = 10000;
    private int pageSize; //每页显示记录数
    private int totalPage;        //总页数
    private int totalResult;    //总记录数
    private int pageNo;    //当前页
    private int currentResult;    //当前记录起始索引
    private String sortField;
    private String sortOrder;
    private String[] sumColumns;
    private Map<String, String> sumResult;

    public Page() {
        this.pageSize = PAGE_SIZE;
        this.pageNo = FIRST_PAGE;
    }

    public Page(String pageNo) {
        this(String.valueOf(PAGE_SIZE), pageNo);
    }

    public Page(String pageSize, String pageNo) {
        try {
            this.pageNo = Integer.parseInt(pageNo);
        } catch (NumberFormatException e) {
            this.pageNo = FIRST_PAGE;
        }
        try {
            this.pageSize = Integer.parseInt(pageSize);
        } catch (NumberFormatException e) {
            this.pageSize = PAGE_SIZE;
        }
    }

    public int getTotalPage() {
        if (totalResult % pageSize == 0) {
            totalPage = totalResult / pageSize;
        } else {
            totalPage = totalResult / pageSize + 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getPageNo() {
        if (pageNo <= 0) {
            pageNo = FIRST_PAGE;
        }
        if (pageNo > getTotalPage()) {
            pageNo = getTotalPage();
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentResult() {
        currentResult = (getPageNo() - 1) * getPageSize();
        if (currentResult < 0) {
            currentResult = 0;
        }
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public String[] getSumColumns() {
        return sumColumns;
    }

    public void setSumColumns(String[] sumColumns) {
        this.sumColumns = sumColumns;
    }

    public boolean hasSumColumns() {
        return sumColumns != null && sumColumns.length > 0;
    }

    public boolean lessThanMaxSumLimit() {
        return totalResult < MAX_SUM_LIMIT;
    }

    public Map<String, String> getSumResult() {
        return sumResult;
    }

    public void setSumResult(Map<String, String> sumResult) {
        this.sumResult = sumResult;
    }

    public String getSortField() {
        return sortField == null ? "" : sortField;
    }

    public void setSortField(String sortField) {
        setSortField(sortField, "");
    }

    public void setSortField(String sortField, String defaultStr) {
        if (sortField == null || sortField.equals("") || sortField.equals("null")) {
            this.sortField = defaultStr;
        } else {
            this.sortField = " ORDER BY " + (sortField.endsWith("IsNum") ? sortField.substring(0, sortField.lastIndexOf("IsNum")) : " CONVERT(" + sortField + " USING gbk)");
        }
    }

    public void setDefaultSortSql(String sortSql) {
        if (this.sortField == null || this.sortField.equals("")) {
            this.sortField = sortSql;
            this.sortOrder = "";
        }
    }

    public String getSortOrder() {
        return sortOrder == null ? "" : sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = (sortOrder == null || sortOrder.equals("null") ? "" : sortOrder);
    }

}
