package xxl.com.json.common.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xxl.com.json.bean.LoginInfo;

/**
 * Created by xxl on 2017/12/29.
 */

public interface ApiService {
    @GET("LoginServlet")
    Call<LoginInfo> login(@Query("username") String username, @Query("password") String password);
}
