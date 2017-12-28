package xxl.com.json.common.okhttp;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import xxl.com.json.utils.NetWorkUtils;

/**
 * Created by xxl on 2017/12/28.
 */

public class CacheRequestInterceptor implements Interceptor {
    private Context mContext;

    public CacheRequestInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetWorkUtils.isNetWorkConnected(mContext)) {
            //设置无网络时只读缓存
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//只读缓存
                    .build();

        }

        return chain.proceed(request);
    }

}
