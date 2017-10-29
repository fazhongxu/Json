package xxl.com.baselibray.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by xxl on 2017/10/23.
 */

public interface HttpCallBackEngine {
    /**
     * 请求成功
     *
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败回调
     *
     * @param e
     */
    void onFailure(Exception e);

    /**
     * 请求执行之前调用的方法，可用于加载进度条，添加默认参数等操作
     *
     * @param context
     * @param params
     */
    void onPreExcute(Context context, Map<String, Object> params);

    //默认回调
    HttpCallBackEngine DEFAULT_CALL_BACK = new HttpCallBackEngine() {
        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onFailure(Exception e) {

        }

        @Override
        public void onPreExcute(Context context, Map<String, Object> params) {
            
        }
    };
}
