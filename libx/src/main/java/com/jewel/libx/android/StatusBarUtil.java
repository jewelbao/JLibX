package com.jewel.libx.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
@SuppressLint({"WrongConstant", "PrivateApi"})
public class StatusBarUtil {

    /**
     * 将状态栏和导航栏调暗
     */
    public static void dimStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 清除状态栏和导航栏所有状态
     */
    public static void resetStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    /**
     * 隐藏状态栏--全屏显示
     */
    public static void hideStatusBar(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = window.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 沉浸状态栏--应用的内容显示在状态栏后面
     * <br>
     * activity的布局文件中的根布局需要设置属性：
     * <pre> android:fitsSystemWindows="true"</pre>
     * <br>
     * style文件中需要设置属性：
     * <pre> android:windowTranslucentStatus="true"</pre>
     */
    public static void immersiveStatusBar(Activity activity, ViewGroup rootView) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            View decorView = activity.getWindow().getDecorView();
            rootView.setFitsSystemWindows(true);
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
