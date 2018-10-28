package xxl.com.baselibray;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 2018/10/18.
 *
 * Description :
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
