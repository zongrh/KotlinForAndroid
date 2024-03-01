package cn.my.mvvm.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.my.mvvm.base.BaseApp;

/**
 * @author : LeeZhaoXing
 */
public class AppUtils {

    /**
     * 获取设备型号
     * <p>如 MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * @return 版本号
     */
    public static String getVersionCode() {
        String versionCode = "";
        try {
            PackageManager pm = BaseApp.Companion.getAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApp.Companion.getAppContext().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            if (pi != null) {
                versionCode = String.valueOf(pi.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;

    }

    /**
     * 获取版本名
     */
    public static String getVersionName() {
        String name;
        try {
            name = BaseApp.Companion.getAppContext().getPackageManager().getPackageInfo(BaseApp.Companion.getAppContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        if (name == null) {
            name = "1";
        }
        return name;
    }

    /**
     * 包名判断是否为主进程
     */
    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getProcessName(context));
    }

    /**
     * 获取进程名称
     */
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0
            );
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据包名判断是否安装该应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return boolean isInstalled  是否安装
     */
    public static Boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        boolean isInstalled = false;
        try {
            PackageInfo pInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_GIDS);
            isInstalled = pInfo != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInstalled;
    }

    public static String getExceptionMsg(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * INTERNAL method that returns the build date of the current APK as a string, or null if
     * unable to determine it.
     *
     * @param context    A valid context. Must not be null.
     * @param dateFormat DateFormat to use to convert from Date to String
     * @return The formatted date, or "Unknown" if unable to determine it.
     */
    public static String getBuildDateAsString(Context context, DateFormat dateFormat) {
        String buildDate;
        try {
            ApplicationInfo ai =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            ZipFile zf = new ZipFile(ai.sourceDir);
            ZipEntry ze = zf.getEntry("classes.dex");
            long time = ze.getTime();
            buildDate = dateFormat.format(new Date(time));
            zf.close();
        } catch (Exception e) {
            buildDate = "Unknown";
        }
        return buildDate;
    }
}
