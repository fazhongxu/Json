package xxl.com.json;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;
import xxl.com.baselibray.BaseApp;
import xxl.com.baselibray.http.HttpUtil;
import xxl.com.baselibray.http.OkHttpEngine;
import xxl.com.json.gen.GreenDaoManager;
import xxl.com.json.util.CrashHandler;
import xxl.com.json.util.SharedPreferenceUtils;

/**
 * Created by xxl on 2017/10/23.
 */

public class App extends BaseApp {
    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init(new OkHttpEngine());

        SharedPreferenceUtils.init(this);

        mContext = this;

        GreenDaoManager.getInstance();

        CrashHandler.getInstance().init(this);

        MobSDK.init(this,"25a0b0e10e9e0");

        CrashReport.initCrashReport(this,"60588e55ec",false);

        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);

    }

    private static class Holder {
        private static App INSTANCE = new App();
    }

    public static App getInstance() {
        return Holder.INSTANCE;
    }

    public Context getContext() {
        return mContext;
    }
}
