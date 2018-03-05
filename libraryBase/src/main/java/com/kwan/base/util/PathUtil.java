package com.kwan.base.util;

import android.os.Environment;

import java.io.File;


public class PathUtil {

    public static long LOW_STORAGE_SIZE = 10 * 1024 * 1024;// 最低存储所需空间
    public static String ROOT_PATH = "/";// 系统根目录
    public static String PARENT_DIR_NAME = "Bazaar";

    public static String TEMP_DIR_NAME = "BZTemp";
    public static String DOWNLOAD_DIR_NAME = "Download";
    public static String CRASH_DIR_NAME = "CrashLog";

//    public static String HEADIMG_DIR_NAME = "FXHeadImg";
//    public static String CACHE_DIR_NAME = "FXCache";
//    public static String APK_FILE_NAME = "loupangou.apk";
//    public static String PRIMY_DB_NAME = "FXDB.db";


    /**
     * 获取SD卡 的路径
     *
     * @return SDCardPath String 已挂载SDCard返回SDCard路径，否则返回"/"(根路径)
     */
    public static String getSDCardPath() {
        String SDCardPath;
        if (isMounted()) {
            SDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            return SDCardPath + File.separator;
        } else {
            SDCardPath = ROOT_PATH;
            return SDCardPath;
        }
    }

    /**
     * 返回APP在手机存储中的根路径，例如：/mnt/sdcard/FXParent/
     */
    public static String getApplicationRootPath() {
        String appRootPath = getSDCardPath() + PARENT_DIR_NAME
                + File.separator;
        try {
            File f = new File(appRootPath);
            if (f.mkdirs() || f.exists()) {
                return appRootPath;
            }
            return getSDCardPath();
        } catch (Exception e) {
            e.printStackTrace();
            return getSDCardPath();
        }
    }

    /**
     * 返回APP在手机存储中的临时文件路径，例如：/mnt/sdcard/FXParent/FXTemp/
     */
    public static String getApplicationTempPath() {
        String appTempPath = getApplicationRootPath()
                + TEMP_DIR_NAME + File.separator;
        try {
            File f = new File(appTempPath);
            if (f.mkdirs() || f.exists()) {
                return appTempPath;
            }
            return getApplicationRootPath();
        } catch (Exception e) {
            e.printStackTrace();
            return getApplicationRootPath();
        }
    }

    /**
     * 返回APP下载根路径文件夹
     */
    public static String getApplicationDownloadPath() {
        String downloadRootPath = getApplicationRootPath()
                + DOWNLOAD_DIR_NAME + File.separator;
        try {
            File f = new File(downloadRootPath);
            if (f.mkdirs() || f.exists()) {
                return downloadRootPath;
            }
            return getApplicationRootPath();
        } catch (Exception e) {
            e.printStackTrace();
            return getApplicationRootPath();
        }
    }

    /**
     * 判断SD卡是否已挂载
     *
     * @return isMounted boolean 已挂载SDCard返回true，否则返回false
     */
    public static boolean isMounted() {
        boolean isMounted = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        return isMounted;
    }

    /**
     * 判断SD卡是否可写入
     *
     * @return isSdCardWrittenable boolean SDCard可写则返回true，否则返回false
     */
    public static boolean isSdCardWrittenable() {
        return Environment.getExternalStorageDirectory()
                .canWrite();

    }

    /**
     * 判断SD卡是否可读取
     *
     * @return isSdCardReadable boolean SDCard可读则返回true，否则返回false
     */
    public static boolean isSdCardReadable() {
        return Environment.getExternalStorageDirectory().canRead();
    }
}
