package xxl.com.json.mvp.demo2;

import android.content.Context;

import xxl.com.baselibray.http.HttpUtil;

/**
 * Created by xxl on 2017/12/7.
 * M 层 数据层
 */

public class Model {
    private static final String BASE_URL = "https://www.baidu.com";
    public HttpUtil request(Context context) {
        HttpUtil http = HttpUtil
                .with(context)
                .get()
                .url(BASE_URL);
        return http;
    }
}
