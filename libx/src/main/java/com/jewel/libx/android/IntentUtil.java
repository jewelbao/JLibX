package com.jewel.libx.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.jewel.libx.TAG;

import java.io.File;
import java.util.List;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/4/24
 */
public class IntentUtil {

    /**
     * 检查有没有应用程序来接受处理你发出的intent
     *
     * @param action Intent动作，例如ACTION_VIEW。
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 打开浏览器
     *
     * @param url 网址
     */
    public static void openBrower(Context context, String url) {
        Intent viewIntent = new
                Intent("android.intent.action.VIEW", Uri.parse(url));
        context.startActivity(viewIntent);
    }

    /**
     * 查看电池使用情况
     */
    public static void openBattery(Context context) {
        Intent intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
        context.startActivity(intentBatteryUsage);
    }

    /**
     * 调用便携式热点和数据共享设置
     */
    public static void openHotspotSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        ComponentName com = new ComponentName("com.android.settings", "com.android.settings.TetherSettings");
        intent.setComponent(com);
        context.startActivity(intent);
    }

    /**
     * 主动回到Home，后台运行
     */
    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(mHomeIntent);
    }

    /**
     * 安装APK
     * @param file apk文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction( "android.intent.action.VIEW" );
        intent.addCategory( "android.intent.category.DEFAULT" );
        intent.setType( "application/vnd.android.package-archive" );
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive" );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 发送短信
     * @param phoneNumber 目标号码
     * @param content 短信内容
     */
    public static void sendSms(Context context, String phoneNumber,String content) {
        Uri uri = Uri.parse( "smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" :phoneNumber));

        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra( "sms_body" , TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     */
    public static void call(Context context, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG.TAG, "Failed to invoke call", e);
        }
    }
}
