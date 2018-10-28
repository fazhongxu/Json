package xxl.com.json.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by xxl on 2017/11/29.
 * acticity管理类
 */

public class ActivityManager {
    private Stack<Activity> mActivities;
    private static ActivityManager sActivityManager = null;

    private ActivityManager() {
        mActivities = new Stack<>();
    }

    /**
     * 单例
     */
    public static ActivityManager getInstance() {
        if (sActivityManager == null) {
            synchronized (ActivityManager.class) {
                if (sActivityManager == null) {
                    sActivityManager = new ActivityManager();
                }
            }
        }
        return sActivityManager;
    }

    /**
     * 添加activity 添加到堆集合
     */
    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 移除activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (mActivities.contains(activity)) {
                mActivities.remove(activity);
            }
            activity.finish();
        }
    }

    /**
     * 移除指定类名的activity
     */
    public void finishActivity(Class<? extends Activity> clazz) {
        for (Activity activity : mActivities) {
            if (clazz.equals(activity.getClass())) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 移除所有activity
     */
    public void finishAllActivity() {
        if (mActivities != null && mActivities.size() != 0) {
            for (int i = 0; i < mActivities.size(); i++) {
                mActivities.get(i).finish();
            }
        }
        if (mActivities != null)
            mActivities.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        finishAllActivity();
        System.exit(0);
    }
}
