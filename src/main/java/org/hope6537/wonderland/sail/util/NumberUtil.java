package org.hope6537.wonderland.sail.util;

import org.hope6537.wonderland.sail.constant.Constant;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数字工具类
 *
 * @author gaoxinyu
 */
public class NumberUtil {
    /**
     * 将数字格式化
     *
     * @param num    数字
     * @param parser 格式化形式
     * @return String
     */
    public static String numFormat(Object num, String parser) {
        DecimalFormat df = new DecimalFormat(parser);
        df.setRoundingMode(RoundingMode.HALF_UP);
        try {
            if (num == null) {
                return df.format(0);
            }
            if (num instanceof String) {
                return df.format(Double.parseDouble(String.valueOf(num).replace("--", "")));
            }
            String stringResult = df.format(num);
            if (Double.parseDouble(stringResult.replace(",", "")) == 0) {
                //此处判断是为了防止出现"-0.000"的情况
                return df.format(0);
            }
            return stringResult;
        } catch (Exception e) {
            LoggerFactory.getLogger(NumberUtil.class).error(Constant.ERROR, e);
            return df.format(0);
        }
    }

    /**
     * 将数字格式化
     *
     * @param num       数字
     * @param precision 保留小数点后位数
     * @return String
     */
    public static String numFormat(Object num, int precision) {
        return numFormat(num, "0" + getZeroStr(precision));
    }

    /**
     * 将数字格式化，默认格式化为2位小数
     *
     * @param num 数字
     * @return String
     */
    public static String numFormat(Object num) {
        return numFormat(num, "0" + getZeroStr(2));
    }

    /**
     * 获得全是0的字符串
     *
     * @param num 0的个数
     * @return String
     */
    public static String getZeroStr(int num) {
        if (num == 0) {
            return "";
        }
        String str = ".";
        for (int i = 0; i < num; i++) {
            str += "0";
        }
        return str;
    }

    /**
     * 保留3位小数，返回0.000格式
     *
     * @param num 数字
     * @return 结果
     */
    public static String decimal3(Object num) {
        return numFormat(num, 3);
    }

    /**
     * 保留5位小数，返回0.00000格式
     *
     * @param num 数字
     * @return 结果
     */
    public static String decimal5(Object num) {
        return numFormat(num, 5);
    }

    /**
     * 对钱进行格式化--最大支持整数部分15位--整数部分每三位用逗号分隔，默认保留2位小数
     *
     * @param num 数字
     * @return 结果
     */
    public static String commaFormat(Object num) {
        return commaFormat(num, 2);
    }

    /**
     * 对钱进行格式化--最大支持整数部分15位--整数部分每三位用逗号分隔
     *
     * @param num       数字
     * @param precision 精度
     * @return 结果
     */
    public static String commaFormat(Object num, int precision) {
        return numFormat(num, "#,##0" + getZeroStr(precision));
    }

    /**
     * 对重量，如果格式化以后，后面是.000,那么不显示后面部分，只显示整数部分
     *
     * @param num 数字
     * @return String
     */
    public static String intOrDouble(String num) {
        String newNum;
        if (num.contains(".")) {
            if (getZeroStr((num.split("\\.")[1].length())).equals(num.split("\\.")[1])) {
                newNum = num.split("\\.")[0];
            } else {
                newNum = num;
            }
        } else {
            newNum = num;
        }
        return newNum;
    }

    /**
     * 去掉小数点后多余的0
     *
     * @param str 字符串
     * @return String
     */
    public static String trimZero(String str) {
        if (str.contains(".")) {
            while (true) {
                if (str.lastIndexOf("0") == (str.length() - 1)) {
                    str = str.substring(0, str.lastIndexOf("0"));
                } else {
                    break;
                }
            }
            if (str.indexOf(".") == (str.length() - 1)) {
                str = str.substring(0, str.indexOf("."));
            }
        }
        return str;
    }

    /**
     * 把数字的钱数，转换成人民币大写
     *
     * @param strNum 阿拉伯数字的钱数
     */
    public static String getChnMoney(String strNum) {
        int n, m, k, i, j, q, p, s;
        int length, subLength, pstn;
        String change, output, subInput;
        output = "";
        if (strNum.equals("")) {
            return null;
        } else {
            length = strNum.length();
            pstn = strNum.indexOf('.'); //小数点的位置

            if (pstn == -1) {
                subLength = length;//获得小数点前的数字
                subInput = strNum;
            } else {
                subLength = pstn;
                subInput = strNum.substring(0, subLength);
            }

            char[] array;
            char[] array2 = {'仟', '佰', '拾'};
            char[] array3 = {'亿', '万', '元', '角', '分'};

            n = subLength / 4;//以千为单位
            m = subLength % 4;

            if (m != 0) {
                for (i = 0; i < (4 - m); i++) {
                    subInput = '0' + subInput;//补充首位的零以便处理
                }
                n = n + 1;
            }
            k = n;

            for (i = 0; i < n; i++) {
                p = 0;
                change = subInput.substring(4 * i, 4 * (i + 1));
                array = change.toCharArray();//转换成数组处理

                for (j = 0; j < 4; j++) {
                    output += formatC(array[j]);//转换成中文
                    if (j < 3) {
                        output += array2[j];//补进单位，当为零是不补（千百十）
                    }
                    p++;
                }

                if (p != 0) {
                    output += array3[3 - k];//补进进制（亿万元分角）
                }
                //把多余的零去掉

                String[] str = {"零仟", "零佰", "零拾"};
                for (s = 0; s < 3; s++) {
                    while (true) {
                        q = output.indexOf(str[s]);
                        if (q != -1) {
                            output = output.substring(0, q) + "零" + output.substring(q + str[s].length());
                        } else {
                            break;
                        }
                    }
                }
                while (true) {
                    q = output.indexOf("零零");
                    if (q != -1) {
                        output = output.substring(0, q) + "零" + output.substring(q + 2);
                    } else {
                        break;
                    }
                }
                String[] str1 = {"零亿", "零万", "零元"};
                for (s = 0; s < 3; s++) {
                    while (true) {
                        q = output.indexOf(str1[s]);
                        if (q != -1) {
                            output = output.substring(0, q) + output.substring(q + 1);
                        } else {
                            break;
                        }
                    }
                }
                k--;
            }

            if (pstn != -1)//小数部分处理
            {
                for (i = 1; i < length - pstn; i++) {
                    if (strNum.charAt(pstn + i) != '0') {
                        output += formatC(strNum.charAt(pstn + i));
                        output += array3[2 + i];
                    } else if (i < 2) {
                        output += "零";
                    } else {
                        output += "";
                    }
                }
            }
            if (output.substring(0, 1).equals("零")) {
                output = output.substring(1);
            }
            if (output.substring(output.length() - 1, output.length()).equals("零")) {
                output = output.substring(0, output.length() - 1);
            }
            return output + "整";
        }
    }

    private static String formatC(char x) {
        String a = "";
        switch (x) {
            case '0':
                a = "零";
                break;
            case '1':
                a = "壹";
                break;
            case '2':
                a = "贰";
                break;
            case '3':
                a = "叁";
                break;
            case '4':
                a = "肆";
                break;
            case '5':
                a = "伍";
                break;
            case '6':
                a = "陆";
                break;
            case '7':
                a = "柒";
                break;
            case '8':
                a = "捌";
                break;
            case '9':
                a = "玖";
                break;
        }
        return a;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
