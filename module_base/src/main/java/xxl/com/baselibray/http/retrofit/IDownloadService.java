package xxl.com.baselibray.http.retrofit;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by xxl on 2018/10/31.
 *
 * Description :
 */

public interface IDownloadService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
