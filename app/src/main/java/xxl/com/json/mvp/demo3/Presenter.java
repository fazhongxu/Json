package xxl.com.json.mvp.demo3;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;
import xxl.com.json.mvp.demo3.base.AbsMvpBasePresenter;

/**
 * Created by xxl on 2017/12/7.
 * P 层 需要持有M层和V层对象
 * 耦合 耦合M 和 V 让V能够显示出M层的数据
 */

public class Presenter extends AbsMvpBasePresenter<View>{
    private Model mModel;

    public Presenter() {
        mModel = new Model();
    }

    /**
     * 请求
     */
    public void request(final Context context) {
        if (getView() != null) {
            getView().loading();
        }
        mModel.request(context)
                .execute(new HttpCallBackEngine() {
                    @Override
                    public void onSuccess(final String result) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getView() != null) {
                                    getView().requestSucess(result);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getView() != null) {
                                    getView().requestFialure("加载出错");
                                }
                            }
                        });
                    }

                    @Override
                    public void onPreExcute(Context context, Map<String, Object> params) {

                    }
                });
    }
}
