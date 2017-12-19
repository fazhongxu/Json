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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xxl.com.json.R;

public class OkHttpActivity extends BaseActivity implements View.OnClickListener{
    private static final String UPLOAD_URL = "https://api.saiwuquan.com/api/upload";
    private File file = new File(Environment.getExternalStorageDirectory(),"aaa.apk");
    private Button mBtnTest;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        initView();
        okHttpClient = new OkHttpClient();
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                RequestBody requestBody = RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file);

                MultipartBody multipartBody = new MultipartBody
                        .Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file",file.getName(),requestBody)
                        .build();

                Request request = new Request
                        .Builder()
                        .post(multipartBody)
                        .url(UPLOAD_URL)
                        .build();

                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("aaa", "onFailure: "+e.getMessage() );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("aaa", "onResponse: ");
                    }
                });
                break;
        }
    }
    private String guessMimeType(String filePath){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(filePath);
        if (TextUtils.isDigitsOnly(mimeType)){
            mimeType = "application/octet-stream";
        }
        return mimeType;
    }
}
