package xxl.com.baselibray.http.retrofit;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xxl on 2018/10/31.
 *
 * Description :
 */

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.107:8080/OkHttpServer/";
    private final Retrofit mRetrofit;

    private RetrofitClient() {
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
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



    private static class Holder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
    public static RetrofitClient getInstance() {
       return Holder.INSTANCE;
    }
}
