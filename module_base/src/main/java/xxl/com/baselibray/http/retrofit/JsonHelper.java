package xxl.com.baselibray.http.retrofit;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xxl on 2018/10/31.
 *
 * Description :
 */

public class JsonHelper {
    public static RequestBody getPostBody(Object object) {
        Gson gson = new Gson();
        String string = gson.toJson(object);
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), string);
    }
}
