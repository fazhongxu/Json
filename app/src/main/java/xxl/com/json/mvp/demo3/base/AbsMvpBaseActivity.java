package xxl.com.json.mvp.demo3.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xxl on 2017/12/11.
 */

public abstract class AbsMvpBaseActivity<V extends IBaseView,P extends AbsMvpBasePresenter<V>> extends AppCompatActivity {
    //activity基类 负责绑定和解绑view 给子类提供创建presenter的抽象方法 获取presenter的方法
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mPresenter == null){
            mPresenter = createPresenter();
        }
        if (mPresenter == null){
            throw new IllegalArgumentException("mPresenter could not be null");
        }

        mPresenter.attachView((V)this);
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取presenter
     */
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
