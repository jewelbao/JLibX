package com.jewel.libx.java;

import android.content.Context;

import com.jewel.libx.android.CompatUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.IntRange;
import androidx.annotation.StringRes;

@SuppressWarnings("unused")
public final class StringUtil {

    private StringUtil() {
        throw new ExceptionInInitializerError();
    }

    /**
     * 格式化string.xml 中的字符串
     */
    public static String get(Context context, @StringRes int stringRes) {
        return CompatUtil.getString(context, stringRes);
    }

    /**
     * 格式化string.xml 中的字符串
     */
    public static String get(Context context, @StringRes int stringRes, Object... args) {
        return String.format(CompatUtil.getString(context, stringRes), args);
    }

    /**
     * 获取格式化的字符串。 当源不包含“％s”时，将在源的末尾添加“％s”
     */
    public static String get(String source, Object arg) {
        if (arg != null) {
            if (!source.contains("%s")) {
                source = source + "%s";
            }
        }
        return String.format(source, arg);
    }

    /**
     * 获取格式化的字符串。 当source不包含“％s”时，将根据args添加相同数量的“％s”
     */
    public static String get(String source, Object... args) {
        if (args != null && args.length > 0) {
            if (!source.contains("%s")) {
                source = source + getFormatBy(args.length);
            }
        }
        return String.format(source, args);
    }

    /**
     * 根据count获得相同数量的“％s”字符串
     * <br>
     * getFormatBy(1) == "%s"
     * <br>
     * getFormatBy(3) == "%s%s%s"
     *
     * @param count "%s" number
     * @return  "%s" * count
     */
    public static String getFormatBy(@IntRange(from = 1) int count) {
        StringBuilder format = new StringBuilder(1);
        for (int i = 1; i <= count; i++) {
            format.append("%s");
        }
        return format.toString();
    }

    /**
     * 判断是否为汉字
     * @return <code>true</code>表示为汉字
     */
    public static boolean isChinese(String sequence) {
        final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        boolean result = false;
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        result = matcher.find();
        return result;
    }

    /**
     * 检测字符串中只能包含：中文、数字、下划线(_)、横线(-)
     */
    public static boolean checkNickname(String sequence) {
        final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w-_]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        return !matcher.find();
    }
}
