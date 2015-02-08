package org.hope6537.string;

import java.util.regex.Pattern;

public class StringRegexUtils {

    public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";
    public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";
    public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";
    public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";
    public static final String date_regexp = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";// 匹锟斤拷锟斤拷锟斤拷
    public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";
    public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";
    public static final String ZIP_regexp = "^[0-9]{6}$";
    public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";
    public static final String non_negative_integers_regexp = "^\\d+$";
    public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";
    public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";
    public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";
    public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";
    public static final String integer_regexp = "^-?\\d+$";
    public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";
    public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
    public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";
    public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";
    public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";
    public static final String letter_regexp = "^[A-Za-z]+$";
    public static final String upward_letter_regexp = "^[A-Z]+$";
    public static final String lower_letter_regexp = "^[a-z]+$";
    public static final String letter_number_regexp = "^[A-Za-z0-9]+$";
    public static final String letter_number_underline_regexp = "^\\w+$";
    public static final String china_regexp = "[\u4E00-\u9FA5]*";

    public static boolean isRegexpValidate(String source, String regexp) {
        return source.matches(regexp);
    }

    public static boolean isRegexpValidateP(String source, String regexp) {
        return Pattern.matches(regexp, source);
    }

    public static void main(String[] args) {

    }

}
