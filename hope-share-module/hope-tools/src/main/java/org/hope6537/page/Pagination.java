package org.hope6537.page;

import java.util.List;

/**
 * 通用分页器.
 * Created by wuyang.zp on 15/5/6.
 */
public class Pagination<T> {
    private static final Integer START_ROW = Integer.valueOf(0);
    private int pageSize = 10;
    private int pageNo = 1;
    private int pageCount;
    private int recordCount;
    private List<T> datas;

    public Pagination() {
    }

    public Pagination(int pageSize, int pageNo, int recordCount) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        this.pageCount = (this.recordCount + this.pageSize - 1) / this.pageSize;
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getStartRow() {
        return (this.pageNo - 1) * this.pageSize + START_ROW.intValue();
    }

    public boolean hasNextPage() {
        return this.pageCount > this.pageNo;
    }

    public boolean isFirstPage() {
        return this.pageNo == 1;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<T> getDatas() {
        return this.datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
