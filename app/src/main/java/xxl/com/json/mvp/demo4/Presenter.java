package xxl.com.json.mvp.demo4;

import android.content.Context;

import xxl.com.json.common.HttpCallBack;
import xxl.com.json.mvp.demo4.base.BasePresenter;

/**
 * Created by xxl on 2018/1/2.
 */

public class Presenter extends BasePresenter<IContract.View> implements IContract.Presenter {

    private Model mModel;

    public Presenter() {
        mModel = new Model();
    }

    @Override
    public void getMessage(final Context context) {
        getView().loading();

        mModel.request(context).execute(new HttpCallBack<String>() {
            @Override
            public void onSucces(String resultBean) {
                getView().onSuccess(resultBean);
            }

            @Override
            public void onFailure(Exception e) {
                getView().onFialure(e);
            }
        });
    }
}
