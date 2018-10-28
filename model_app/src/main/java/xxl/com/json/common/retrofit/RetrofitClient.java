package xxl.com.json.common.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xxl on 2017/12/29.
 */

public class RetrofitClient {

    private static ApiService mApiService;

    static {
        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.107:8080/OkHttpServer/")
                .addConverterFactory(GsonConverterFactory.create())//添加Gson解析工厂
                .client(okHttpClient)//添加okHttpClient
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    public static ApiService getRetrofit() {
        return mApiService;
    }
}
