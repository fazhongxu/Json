package xxl.com.json.mvp.demo4.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xxl on 2018/1/2.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        mPresenter = createPresenter();

        if (mPresenter == null) {
            throw new IllegalArgumentException("mPresenter could not be null");
        }

        mPresenter.attachView(this);//绑定View

        initView();

        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P createPresenter();

    protected abstract void setContentView();

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();//解绑View
    }
}
