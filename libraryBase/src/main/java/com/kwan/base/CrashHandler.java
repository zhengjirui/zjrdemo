package com.kwan.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.kwan.base.util.PathUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * APP的崩溃异常处理类
 *
 * @author zhaoyun
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();
    /**
     * 自定义崩溃异常处理的成功标识
     **/
    private static boolean myTag = false;
    /**
     * 系统默认的UncaughtException处理类
     **/
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler单例
     **/
    private static CrashHandler mCrashHandlerInstance;
    /**
     * 程序的Context对象
     **/
    private Context mContext;
    /**
     * 用来存储设备信息和异常信息
     **/
    private Map<String, String> infos = new HashMap<String, String>();
    /**
     * 用于格式化日期,作为日志文件名的一部分
     **/
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 将空参构造private，保证只有一个CrashHandler实例
     */
    private CrashHandler() {
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));  //如果怕时区错误，加上这行代码就OK
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (mCrashHandlerInstance == null) {
            mCrashHandlerInstance = new CrashHandler();
        }
        return mCrashHandlerInstance;
    }

    /**
     * 初始化崩溃异常监听设置
     *
     * @param context 上下文对象
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 当UncaughtException发生时会回调该函数来处理
     *
     * @param thread 系统主线程
     * @param ex     被捕获到的崩溃异常对象
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!myTag) {
            myTag = handleException(ex);
        }
        if (!myTag && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            waitAndKillApp();
        }
    }

    /**
     * 等待3秒(主要用来把提示信息显示给用户)后，退出APP
     */
    private void waitAndKillApp() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Logger.e(TAG + " error : ", e);
        }
        AppManager.getAppManager().AppExit();
    }

    /**
     * 自定义对崩溃异常的处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 被捕获到的崩溃异常对象
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        String fileName = saveCrashInfoFile(ex, infos);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param context 上下文对象
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Logger.e(TAG + "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Logger.d(TAG + field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logger.e(TAG + " an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存崩溃异常信息到文件中
     * 返回文件名称,便于将文件传送到服务器
     *
     * @param ex     被捕获到的崩溃异常对象
     * @param mInfos APP的基本数据封装
     * @return fileName 保存在本地的崩溃异常记录的文件名
     */
    private String saveCrashInfoFile(Throwable ex, Map<String, String> mInfos) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                String path = PathUtil.getApplicationRootPath() + PathUtil.CRASH_DIR_NAME + File.separator;

                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
//			System.out.println(TAG+"my:"+fileName);
            return fileName;
        } catch (Exception e) {
            Logger.e(TAG + "an error occured while writing file...", e);
        }
        return null;
    }

}

