package xxl.com.baselibray.http.retrofit;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xxl on 2018/10/31.
 * <p>
 * Description :
 */

public class RetrofitClient {
    private Retrofit mRetrofit;

    private static HashMap<String, RetrofitClient> mUrlMap = new LinkedHashMap<>();


    private RetrofitClient(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(TIME_OUT,TimeUnit.MICROSECONDS)
//                .connectTimeout(TIME_OUT,TimeUnit.MICROSECONDS)
//                .writeTimeout(TIME_OUT,TimeUnit.MICROSECONDS)
                //.addInterceptor()
                //.addNetworkInterceptor()
                //.cache()
                .build();


        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static RetrofitClient getInstance(String url) {
        RetrofitClient retrofitClient = mUrlMap.get(url);
        if (null == retrofitClient) {
            synchronized (RetrofitClient.class) {
                //if (null == retrofitClient) {
                retrofitClient = new RetrofitClient(url);
                mUrlMap.put(url, retrofitClient);
                //}
            }
        }
        return retrofitClient;
    }

    public static RetrofitClient getInstance(int type) {
        return getInstance(new HostImpl().getBaseUrl(type));
    }

    public static RetrofitClient getInstance() {
        return getInstance(0);
    }

}
