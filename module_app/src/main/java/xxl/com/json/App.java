package xxl.com.json;

import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;
import com.xxl.json.im.RongCloudHelpter;

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

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init(new OkHttpEngine());

        SharedPreferenceUtils.init(this);

        GreenDaoManager.getInstance();

        CrashHandler.getInstance().init(this);
        //三方分享
        MobSDK.init(this,"25a0b0e10e9e0");
        //Bugly
        CrashReport.initCrashReport(this,"60588e55ec",false);
        //JPush
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        // 融云
        //RongIM.init(this);
        RongCloudHelpter.getInstance().init();
    }



}
