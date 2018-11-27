package com.mili.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TeeMo111 on 2018/11/27.
 */
public class FormatUtils {
    /**
     * 过滤String中的空格换行
     *
     * @param originalString
     * @return
     */
    public static String formatString(String originalString) {
        Pattern par = Pattern.compile("\\s*|\t|\r|\n");
        Matcher mch = par.matcher(originalString);
        String result = mch.replaceAll("");
        return result;
    }
}
