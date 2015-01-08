package org.hope6537.wonderland.sail.util;

/**
 * @author gaoxinyu
 */
public class ExcelUtil {
    /**
     * 将excel的数字转换成字母（列号）
     *
     * @param index
     * @return String
     * @since 2.0.0
     */
    public static String excelIndexToLetter(int index) {
        String letter = "";
        int remainder = index % 26;
        letter = ((char) (remainder + (int) 'A')) + letter;
        index = (index - remainder) / 26;
        while (index > 0) {
            index--;
            remainder = index % 26;
            index = (index - remainder) / 26;
            letter = ((char) (remainder + (int) 'A')) + letter;
        }
        return letter;
    }
}
