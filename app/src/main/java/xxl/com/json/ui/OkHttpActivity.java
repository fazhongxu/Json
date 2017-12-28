package xxl.com.json.ui;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xxl.com.json.R;
import xxl.com.json.common.okhttp.CacheRequestInterceptor;
import xxl.com.json.common.okhttp.CacheResponseInterceptor;
import xxl.com.json.common.okhttp.ExMultipartBody;
import xxl.com.json.common.okhttp.UpLoadListener;

public class OkHttpActivity extends BaseActivity implements View.OnClickListener {
    //    private static final String URL = "https://api.saiwuquan.com/api/upload";
    private static final String URL = "https://baidu.com";
    private File file = new File(Environment.getExternalStorageDirectory(), "aaa.apk");
    private Button mBtnTest;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        initView();

        File cacheFile = new File(Environment.getExternalStorageDirectory(), "OkHttpCache");
        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);

        okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new CacheRequestInterceptor(this))//没有网络 只读缓存
                .addNetworkInterceptor(new CacheResponseInterceptor())//添加网络缓存 有网络，缓存策略设置过期时间30s
                .cache(cache)
                .build();
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
//                upload();
                cache();
                break;
        }
    }

    /**
     * 緩存
     */
    private void cache() {

        Request request = new Request.Builder()
                .url(URL)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aaa", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("aaa", "onResponse: 缓存--" + response.cacheResponse() + "---数据" + response.body().string());
            }
        });
    }

    /**
     * 带进度监听的 文件上传
     */
    private void upload() {

        RequestBody requestBody = RequestBody
                .create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file);

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", this.file.getName(), requestBody)
                .build();

        //静态代理
        ExMultipartBody exMultipartBody = new ExMultipartBody(multipartBody, new UpLoadListener() {
            @Override
            public void progress(final long totalSize, final long currentLength) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpActivity.this, "" + currentLength + "/" + totalSize, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Request request = new Request.Builder()
                .url(URL)
                .post(exMultipartBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aaa", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("aaa", "onResponse: " + response.body().string());
            }
        });
    }

    private String guessMimeType(String filePath) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(filePath);
        if (TextUtils.isDigitsOnly(mimeType)) {
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }
}
