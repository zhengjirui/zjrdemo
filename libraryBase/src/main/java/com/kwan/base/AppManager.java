package com.kwan.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.kwan.base.util.SysUtil;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public Stack<Activity> getAll() {
        return activityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的，也就是我们在APP中现在所看到的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 当Activity被调用onDestory时从堆栈中去除
     *
     * @param activity 要移除的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null)
            activityStack.remove(activity);

    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {

        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {

        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity除了指定Activity外
     */
    public void finishAllActivityWithoutSpeciality(Class<?> cls) {

        Stack<Activity> tempActivityStack = new Stack<Activity>();
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                if (!activityStack.get(i).getClass().equals(cls)) {
                    activityStack.get(i).finish();
                } else {
                    tempActivityStack.add(activityStack.get(i));
                }
            }
        }
        activityStack.clear();
        activityStack.addAll(tempActivityStack);
    }

    /**
     * 结束所有Activity除了指定Activity外
     */
    public void finishAllActivityWithoutThis() {

        Activity act = activityStack.lastElement();
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && !act.equals(activityStack.get(i))) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        activityStack.add(act);
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {

        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) BaseApplication.getInstance()
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(SysUtil.getAppPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}