package xxl.com.json;

import android.app.Application;

import xxl.com.baselibray.http.HttpUtil;
import xxl.com.baselibray.http.OkHttpEngine;

/**
 * Created by xxl on 2017/10/23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtil.init(new OkHttpEngine());
    }
}
