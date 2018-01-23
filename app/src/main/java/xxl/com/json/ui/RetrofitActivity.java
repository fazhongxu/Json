package xxl.com.json.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xxl.com.json.R;
import xxl.com.json.bean.LoginInfo;
import xxl.com.json.common.retrofit.ApiService;
import xxl.com.json.common.retrofit.RetrofitClient;
import xxl.com.json.ui.base.BaseActivity;

public class RetrofitActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        initView();
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                ApiService retrofit = RetrofitClient.getRetrofit();
                Call<LoginInfo> call = retrofit.login("json", "123");

                call.enqueue(new Callback<LoginInfo>() {
                    @Override
                    public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                        Log.e("aaa", "onResponse: "+response.body().getData().getUserName()+"--"+response.body().getData().getPassword());
                    }

                    @Override
                    public void onFailure(Call<LoginInfo> call, Throwable t) {
                        Log.e("aaa", "onFailure: "+t.getMessage());
                    }
                });
                break;
        }
    }
}
