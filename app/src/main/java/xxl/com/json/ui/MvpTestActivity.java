package xxl.com.json.ui;


import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import xxl.com.json.R;
import xxl.com.json.mvp.demo4.IContract;
import xxl.com.json.mvp.demo4.Presenter;
import xxl.com.json.mvp.demo4.base.BaseMvpActivity;

public class MvpTestActivity extends BaseMvpActivity<Presenter> implements IContract.View, View.OnClickListener {
    private Button mBtnTest;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_mvp_test);
    }

    @Override
    public void loading() {
        Toast.makeText(this, "加载中...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MvpTestActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFialure(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MvpTestActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        getPresenter().getMessage(this);
    }
}



/*import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import xxl.com.json.R;
import xxl.com.json.mvp.demo3.Presenter;
import xxl.com.json.mvp.demo3.View;
import xxl.com.json.mvp.demo3.base.AbsMvpBaseActivity;

public class MvpTestActivity extends AbsMvpBaseActivity<View, Presenter> implements android.view.View.OnClickListener, View {
    private Button mBtnTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);
        initView();
    }

   private void initView(){
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(android.view.View v) {
        getPresenter().request(this);
    }

    @Override
    public void loading() {
        Toast.makeText(this, "加载中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestSucess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestFialure(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter();
    }
}*/
