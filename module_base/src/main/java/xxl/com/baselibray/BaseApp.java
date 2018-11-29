package xxl.com.baselibray;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 2018/10/18.
 *
 * Description :
 */

public class BaseApp extends Application {
    private static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        mApplication = this;
    }

    /**
     * 获取当前进程名
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    private static class Holder {
        private static BaseApp INSTANCE = new BaseApp();
    }

    public static BaseApp getInstance() {
        return Holder.INSTANCE;
    }

    public Application getContext() {
        return mApplication;
    }
}
