package xxl.com.json.mvp.demo4.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        if (mPresenter != null) {//子类可以不使用Presenter
            mPresenter.attachView(this);//绑定View
        }

        initView();

        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract P createPresenter();

    protected abstract void setContentView();

    public P getPresenter() {
        if (mPresenter == null){
            throw new IllegalStateException("The return value of createPresenter can not be empty");
        }
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();//解绑View
        }
    }
}
