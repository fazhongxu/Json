package xxl.com.json.mvp.demo4;

import android.content.Context;

import xxl.com.baselibray.http.HttpUtil;

/**
 * Created by xxl on 2018/1/2.
 */

public class Model implements IContract.Model {
    private static final String BASE_URL = "https://www.baidu.com";
    @Override
    public HttpUtil request(Context context) {
       return HttpUtil.with(context)
                .get()
                .url(BASE_URL);
    }
}
