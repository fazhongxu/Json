package xxl.com.json;

import android.app.Application;
import android.content.SharedPreferences;

import xxl.com.baselibray.http.HttpUtil;
import xxl.com.baselibray.http.OkHttpEngine;
import xxl.com.json.utils.SharedPreferenceUtils;

/**
 * Created by xxl on 2017/10/23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init(new OkHttpEngine());

        SharedPreferenceUtils.init(this);
    }
}
