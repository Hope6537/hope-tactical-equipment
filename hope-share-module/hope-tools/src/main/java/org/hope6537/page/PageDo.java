package org.hope6537.page;

import java.io.Serializable;

/**
 * 分页基础DO.
 * Created by wuyang.zp on 15/5/28.
 */
public class PageDo implements Serializable {

    private static final long serialVersionUID = -4299676782228364850L;
    /**
     * 结果行数
     */
    private Integer limit = 100;
    /**
     * 起始行 从0开始
     */
    private Integer offset;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
