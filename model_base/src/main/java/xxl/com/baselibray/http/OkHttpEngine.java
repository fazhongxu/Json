package xxl.com.baselibray.http;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xxl on 2017/10/23.
 */

public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient;

    public OkHttpEngine() {
        mOkHttpClient = new OkHttpClient.Builder().build();
    }

    @Override
    public void get(String url, Map<String, Object> params, final HttpCallBackEngine callBack) {
        final Request request = new Request.Builder()
                .url(HttpUtil.joinParams(url, params))
                .build();
        Call call = mOkHttpClient.newCall(request);
        Log.e("aaa", "get: " + HttpUtil.joinParams(url, params));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onSuccess(result);
            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, final HttpCallBackEngine callBack) {
        RequestBody requestBody = appendBody(params);
        Log.e("aaa", "post: " + HttpUtil.joinParams(url, params));
        final Request.Builder request = new Request.Builder();
        Request build = request
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(build);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                callBack.onSuccess(result);
            }
        });

    }

    //拼接Post请求参数
    private RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                //判断是单个文件还是文件集合list
                if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));
                } else if (value instanceof List) {
                    List<File> list = (List<File>) value;
                    for (int i = 0; i < list.size(); i++) {
                        //获取list中的单个文件
                        File file = list.get(i);
                        builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessMimeType(list.get(i).getAbsolutePath())), file));
                    }
                }
            }
        }
    }

    //猜测文件类型
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
