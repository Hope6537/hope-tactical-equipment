package org.hope6537.piyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.hope6537.context.ApplicationConstant;
import org.slf4j.LoggerFactory;

/**
 * 汉字转换位汉语拼音，英文字符不变
 */
public class Cn2Spell {

    public static String converterToFirstSpell(String chines) {
        return getSpellForZh(chines, true);
    }

    public static String converterToSpell(String chines) {
        return getSpellForZh(chines, false);
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变
     *
     * @param chines           汉字
     * @param returnFirstSpell true返回拼音的首字母，false返回整个拼音
     * @return 拼音
     */
    private static String getSpellForZh(String chines, boolean returnFirstSpell) {
        if (chines == null || "".equals(chines)) {
            return "";
        }
        StringBuilder pinyinName = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] nameChar = chines.toCharArray();
        for (char zhChar : nameChar) {
            if (zhChar > 128) {
                String spell = returnFirstSpell ? getFirstSpellForChar(zhChar, defaultFormat) : getSpellForChar(zhChar, defaultFormat);
                pinyinName.append(spell);
            } else {
                pinyinName.append(zhChar);
            }
        }
        return pinyinName.toString();
    }

    private static String getSpellForChar(char zhChar, HanyuPinyinOutputFormat defaultFormat) {
        try {
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(zhChar, defaultFormat);
            if (pinyin != null && pinyin.length != 0) {
                return pinyin[0];
            } else {
                return String.valueOf(zhChar);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LoggerFactory.getLogger(Cn2Spell.class).error(ApplicationConstant.ERROR,e);
        }
        return "";
    }

    private static String getFirstSpellForChar(char zhChar, HanyuPinyinOutputFormat defaultFormat) {
        return getSpellForChar(zhChar, defaultFormat).substring(0, 1);
    }

    public static void main(String[] args) {
        System.out.println(Cn2Spell.converterToSpell("我马上"));
        System.out.println(Cn2Spell.converterToFirstSpell("我马上"));
    }
} 