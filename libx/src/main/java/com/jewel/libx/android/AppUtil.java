package com.jewel.libx.android;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.usage.StorageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jewel.libx.TAG;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import static com.jewel.libx.BuildConfig.VERSION_CODE;
import static com.jewel.libx.BuildConfig.VERSION_NAME;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/2/19
 */
public final class AppUtil {

    /**
     * 判断当前App处于前台还是后台状态
     *
     * @return <code>true</code>表示处于后台
     */
    public static boolean isApplicationBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);

        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否平板（官方用法）
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断当前设备是否为手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断当前手机是否处于锁屏(睡眠)状态
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

    /**
     * 唤醒屏幕并解锁
     */
    public static void wakeUpAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock(TAG.TAG);
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, TAG.TAG);
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

    /**
     * 获取当前应用程序下所有Activity
     *
     * @return 此项目的公共名称列表。 来自“android：name”属性。
     */
    public static ArrayList<String> getActivities(Context ctx) {
        ArrayList<String> result = new ArrayList<String>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setPackage(ctx.getPackageName());
        for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(intent, 0)) {
            result.add(info.activityInfo.name);
        }
        return result;
    }

    /**
     * 启动APK的默认Activity
     */
    public static void startApkActivity(final Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(packageName, 0);
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setPackage(pi.packageName);

            List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String className = ri.activityInfo.name;
                intent.setComponent(new ComponentName(packageName, className));
                ctx.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备上已安装的应用列表，包括无法启动甚至没有图标的系统应用
     *
     * @return {@link AppInfo}列表
     * @throws PackageManager.NameNotFoundException
     */
    public static List<AppInfo> getAllApps(Context context) throws PackageManager.NameNotFoundException {
        List<AppInfo> appInfoList = new ArrayList<>();

        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        if (packages == null || packages.isEmpty()) {
            return appInfoList;
        }
        for (PackageInfo info : packages) {
            Log.e(TAG.TAG, pm.getApplicationLabel(pm.getApplicationInfo(info.packageName, PackageManager.GET_META_DATA)).toString());
            AppInfo appInfo = new AppInfo();
            ApplicationInfo applicationInfo = info.applicationInfo;
            String packageName = info.packageName;
            appInfo.name = getApplicationName(pm, packageName);
            if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
                // APP is currently installed on external/removable/unprotected storage.
                appInfo.installLocation = AppInfo.INSTALL_EXTERNAL;
            } else {
                appInfo.installLocation = AppInfo.INSTALL_INTERNAL;
            }
            appInfo.isSystem = (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
            appInfo.launcherIcon = pm.getApplicationIcon(packageName);
            appInfo.packageName = packageName;
            appInfo.versionName = info.versionName;
            appInfo.version = info.versionCode;
            appInfo.firstInstallTime = info.firstInstallTime;
            appInfo.lastUpdateTime = info.lastUpdateTime;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                appInfo.installLocation = info.installLocation;
            }
            appInfoList.add(appInfo);
        }
        return appInfoList;
    }

    /**
     * 获取设备上已安装并且可启动的应用列表
     *
     * @return {@link AppInfo}列表
     * @throws PackageManager.NameNotFoundException
     */
    public static List<AppInfo> getAppList(Context context) throws PackageManager.NameNotFoundException {
        List<AppInfo> appInfoList = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (activities == null || activities.isEmpty()) {
            return appInfoList;
        }
        PackageManager pm = context.getPackageManager();
        for (ResolveInfo info : activities) {
            Log.e(TAG.TAG, pm.getApplicationLabel(pm.getApplicationInfo(info.activityInfo.packageName, PackageManager.GET_META_DATA)).toString());
            AppInfo appInfo = new AppInfo();
            ApplicationInfo applicationInfo = info.activityInfo.applicationInfo;
            String packageName = info.activityInfo.packageName;
            appInfo.name = getApplicationName(pm, packageName);
            if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
                // APP is currently installed on external/removable/unprotected storage.
                appInfo.installLocation = AppInfo.INSTALL_EXTERNAL;
            } else {
                appInfo.installLocation = AppInfo.INSTALL_INTERNAL;
            }
            appInfo.isSystem = (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
            appInfo.launcherIcon = pm.getApplicationIcon(packageName);
            appInfo.packageName = packageName;
            appInfoList.add(appInfo);
        }
        return appInfoList;
    }


    // see:https://blog.csdn.net/qq_23373271/article/details/73277390
    public static void xx(Context context) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            StorageStatsManager statsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
            if (statsManager != null) {
                statsManager.getFreeBytes(UUID.fromString(""));
            }
        }
    }

    public static String getApplicationName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        return getApplicationName(pm, packageName);
    }

    private static String getApplicationName(PackageManager pm, String packageName) {
        try {
            return pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "unKnow";
    }

    /**
     * 收集设备信息，用于信息统计分析
     *
     * @return {@link Properties}
     */
    public static Properties collectDeviceInfo(Context context) {
        Properties mDeviceCrashInfo = new Properties();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG.TAG, "Error while collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG.TAG, "Error while collect crash info", e);
            }
        }
        return mDeviceCrashInfo;
    }

    /**
     * 收集设备信息，用于信息统计分析
     *
     * @return String
     */
    public static String collectDeviceInfoStr(Context context) {
        Properties prop = collectDeviceInfo(context);
        Set deviceInfos = prop.keySet();
        StringBuilder deviceInfoStr = new StringBuilder("{\n");
        for (Iterator iter = deviceInfos.iterator(); iter.hasNext(); ) {
            Object item = iter.next();
            deviceInfoStr.append("\t\t\t").append(item).append(":").append(prop.get(item)).append(", \n");
        }
        deviceInfoStr.append("}");
        return deviceInfoStr.toString();
    }

    public static class AppInfo {

        public static final int INSTALL_INTERNAL = 1;
        public static final int INSTALL_EXTERNAL = 2;

        private Drawable launcherIcon;
        private String name;
        private String packageName;
        private String versionName;
        private long version;
        private long firstInstallTime;
        private long lastUpdateTime;
        private boolean isSystem;
        private int installLocation;
        private boolean isRunning;

        public String getName() {
            return name;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public long getVersion() {
            return version;
        }

        public long getFirstInstallTime() {
            return firstInstallTime;
        }

        public Drawable getLauncherIcon() {
            return launcherIcon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public boolean isRunning() {
            return isRunning;
        }

        /**
         * The install location requested by the package. From the
         * {@link android.R.attr#installLocation} attribute, one of
         * {@link PackageInfo#INSTALL_LOCATION_AUTO}, {@link PackageInfo#INSTALL_LOCATION_INTERNAL_ONLY},
         * {@link PackageInfo#INSTALL_LOCATION_PREFER_EXTERNAL}
         *
         * @return
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public int getInstallLocation() {
            return installLocation;
        }
    }
}
