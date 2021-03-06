package com.jewel.libx.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.TextView;

import com.jewel.libx.TAG;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.viewpager.widget.ViewPager;

/**
 * @author jewelbao
 * @version 1.0
 * @since 2016/9/1
 */
@SuppressWarnings("ALL")
public class CompatUtil {

    public static void setOnPageChangeListener(ViewPager v, ViewPager.OnPageChangeListener p) {
        if (isBelowVersion(Build.VERSION_CODES.M)) {
            v.setOnPageChangeListener(p);
        } else {
            v.addOnPageChangeListener(p);
        }
    }

    /**
     * Set the background to a given Drawable, or remove the background.
     *
     * @param drawable The Drawable to use as the background, or null to remove the background
     */
    public static void setBackground(View view, Drawable drawable) {
        if (isBelowVersion(Build.VERSION_CODES.JELLY_BEAN)) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    /**
     * @see #setBackground(View, Drawable)
     */
    public static void setBackground(View view, @DrawableRes int drawableRes) {
        if (isBelowVersion(Build.VERSION_CODES.JELLY_BEAN)) {
            view.setBackgroundDrawable(getDrawable(view.getContext(), drawableRes));
        } else {
            view.setBackground(getDrawable(view.getContext(), drawableRes));
        }
    }

    /**
     * Sets the text appearance from the specified style resource.
     * Use a framework-defined {@code TextAppearance}  style like
     * {@link android.R.style#TextAppearance_Material_Body1 @android:style/TextAppearance.Material.Body1}
     * or see {@link android.R.styleable#TextAppearance TextAppearance} for the
     * set of attributes that can be used in a custom style.
     *
     * @param appearanceRes the resource identifier of the style to apply
     */
    public static void setTextAppearance(TextView view, @StyleRes int appearanceRes) {
        if (isBelowVersion(Build.VERSION_CODES.M)) {
            view.setTextAppearance(view.getContext(), appearanceRes);
        } else {
            view.setTextAppearance(appearanceRes);
        }
    }

    /**
     * Returns a drawable object associated with a particular resource ID and styled for the current theme.
     *
     * @param drawableRes An object that can be used to draw this resource, or null if the resource could not be resolved.
     * @return An object that can be used to draw this resource.
     */
    public static Drawable getDrawable(Context context, @DrawableRes int drawableRes) {
        if (isBelowVersion(Build.VERSION_CODES.LOLLIPOP)) {
            return context.getResources().getDrawable(drawableRes);
        } else {
            return context.getDrawable(drawableRes);
        }
    }

    /**
     * Returns a localized string from the application's package's default string table.
     *
     * @param stringRes Resource id for the string
     * @return The string data associated with the resource, stripped of styled
     * text information.
     */
    public static String getString(Context context, @StringRes int stringRes) {
        if (isBelowVersion(Build.VERSION_CODES.LOLLIPOP)) {
            return context.getResources().getString(stringRes);
        } else {
            return context.getString(stringRes);
        }
    }

    /**
     * Returns a color integer associated with a particular resource ID.
     * If the resource holds a complex {@link android.content.res.ColorStateList}, then the default color from the set is returned.
     *
     * @param colorRes The desired resource identifier, as generated by the aapt tool. This integer encodes the package, type, and resource entry. The value 0 is an invalid identifier.
     * @return A single color value in the form 0xAARRGGBB.
     */
    public static int getColor(Context context, @ColorRes int colorRes) {
        if (isBelowVersion(Build.VERSION_CODES.M)) {
            return context.getResources().getColor(colorRes);
        } else {
            return context.getResources().getColor(colorRes, null);
        }
    }

    /**
     * Sets the relative padding. The view may add on the space required to display the scrollbars, depending on the style and visibility of the scrollbars.
     *
     * @param left   the start/left padding in pixels
     * @param top    the top padding in pixels
     * @param right  the end/right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public static void setPadding(TextView view, int left, int top, int right, int bottom) {
        if (isBelowVersion(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)) {
            view.setPaddingRelative(left, top, right, bottom);
        } else {
            view.setPadding(left, top, right, bottom);
        }
    }

    /**
     * Clear the background of the specified view
     *
     * @param views the specified view
     */
    public static void clearBackground(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    Drawable drawable = view.getBackground();
                    if (drawable != null) {
                        if (isBelowVersion(Build.VERSION_CODES.JELLY_BEAN)) {
                            view.setBackgroundDrawable(null);
                        } else {
                            view.setBackground(null);
                        }
                        drawable.setCallback(null);
                    }
                }
            }
        }
    }

    /**
     * Asynchronously evaluates JavaScript in the context of the currently displayed page.
     * If non-null, |resultCallback| will be invoked with any result returned from that execution.
     * This method must be called on the UI thread and the callback will be made on the UI thread.
     *
     * @param webView    the webviwe for execute js
     * @param javaScript the JavaScript to execute.
     * @param jsCallback A callback to be invoked when the script execution completes with the result of the execution (if any).
     *                   May be null if no notificaion of the result is required. Use in target N or later.
     */
    public static void evaluateJavascript(WebView webView, String javaScript, ValueCallback<String> jsCallback) {
        if (webView == null) {
            Log.e(TAG.TAG, "webView对象为空，JS事件调用无法执行");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(javaScript, jsCallback);
        } else {
            webView.loadUrl(javaScript);
        }
    }

    /**
     * Compile version is less than version
     */
    public static boolean isBelowVersion(int version) {
        if (Build.VERSION.SDK_INT < version) {
            return true;
        } else
            return false;
    }
}
