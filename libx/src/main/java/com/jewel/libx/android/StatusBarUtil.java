package com.jewel.libx.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
@SuppressLint({"WrongConstant", "PrivateApi"})
public class StatusBarUtil {

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 收起状态栏
     */
    public static void collapseStatusBar(Context ctx) {
        Object sbservice = ctx.getSystemService("statusbar");
        try {
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method collapse;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                collapse = statusBarManager.getMethod("collapsePanels");
            } else {
                collapse = statusBarManager.getMethod("collapse");
            }
            collapse.invoke(sbservice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 展开状态栏
     */
    public static void expandStatusBar(Context ctx) {
        Object sbservice = ctx.getSystemService("statusbar");
        try {
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand;
            if (Build.VERSION.SDK_INT >= 17) {
                expand = statusBarManager.getMethod("expandNotificationsPanel");
            } else {
                expand = statusBarManager.getMethod("expand");
            }
            expand.invoke(sbservice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
