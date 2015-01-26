package org.hope6537.string;

import java.util.Arrays;
import java.util.List;

/**
 * @author hope6537
 */
public class StringUtil {
    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    //根据“，”分割，将字符串转成List
    public static List<String> StringToList(String str) {
        String[] returnStr = str.split(",");
        return Arrays.asList(returnStr);
    }

    //去掉最后一个字符
    public static StringBuilder deleteLastChar(StringBuilder StrBuilder) {
        if (StrBuilder.length() > 1) {
            StrBuilder.deleteCharAt(StrBuilder.length() - 1);
        }
        return StrBuilder;
    }

    public static String getBusinessIdDate(String operateTime) {
        return operateTime.replace("-", "").substring(2, 8);
    }
}
