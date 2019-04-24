package com.jewel.libx.java;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
public class ArraysUtil {

    /**
     * 泛型ArrayList转数组
     * @param cls 具象类型
     * @param items 泛型ArrayListt
     * @param <T> 泛型类
     * @return 泛型数组
     */
    public static <T> T[] toArray(Class<?> cls, ArrayList<T> items) {
        if (items == null || items.size() == 0) {
            return (T[]) Array.newInstance(cls, 0);
        }
        return items.toArray((T[]) Array.newInstance(cls, items.size()));
    }
}
