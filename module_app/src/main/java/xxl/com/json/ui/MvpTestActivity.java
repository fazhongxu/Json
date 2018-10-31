package xxl.com.json.ui;


import android.Manifest;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import xxl.com.baselibray.http.retrofit.DownloadUtil;
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


        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory,"test.apk");
        RxPermissions rxPermissions = new RxPermissions(this);

        Disposable subscribe = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        DownloadUtil.download("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk", file.getPath())
                                .observeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(s -> {
                                    Toast.makeText(this, "文件已下载"+s, Toast.LENGTH_SHORT).show();
                                })
                        ;
                    } else {
                        Toast.makeText(this, "没有读写Sd卡权限", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}