package xxl.com.json;

import android.app.Application;
import android.content.Context;

import xxl.com.baselibray.http.HttpUtil;
import xxl.com.baselibray.http.OkHttpEngine;
import xxl.com.json.gen.GreenDaoManager;
import xxl.com.json.util.SharedPreferenceUtils;

/**
 * Created by xxl on 2017/10/23.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init(new OkHttpEngine());

        SharedPreferenceUtils.init(this);

        mContext = getApplicationContext();

        GreenDaoManager.getInstance();
    }

    public static Context getContext(){
        return mContext;
    }
}
