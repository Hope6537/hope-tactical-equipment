package org.hope6537.context;

import java.util.Collection;

/**
 * Created by Zhaopeng-Rabook on 15-1-9.
 */
public class ApplicationConstant {

    public static final int EFFECTIVE_LINE_ONE = 1;
    public static final int EFFECTIVE_LINE_ZERO = 0;

    public static final String YES = "是";
    public static final String NO = "否";

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String FOUNDER = "创建者";
    public static final String READER = "只读";
    public static final String WRITER = "读写";

    public static final String STATUS_JUDGE = "待审核";
    public static final String STATUS_NORMAL = "正常";
    public static final String STATUS_DIE = "不可用";

    public static boolean notNull(Object o) {
        if (o instanceof String) {
            return o != null && !((String) o).isEmpty();
        }
        if (o instanceof Collection) {
            return o != null && !((Collection) o).isEmpty();
        }
        return o != null;
    }

}
