package xxl.com.json.ui;

import android.os.Bundle;
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
}
