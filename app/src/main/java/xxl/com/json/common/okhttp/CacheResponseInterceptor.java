package xxl.com.json.common.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by xxl on 2017/12/28.
 * OkHttp缓存
 */

public class CacheResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取下一级传递过来的Response
        Response response = chain.proceed(chain.request());//chain.request() 获取上一级传入的request
        //处理过之后返回给上一级
        response = response.newBuilder()
                .removeHeader("Cache-Control")//移除缓存策略
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "max-age=" + 30).build();  //max-age缓存策略 缓存过期时间
        return response;
    }
}
