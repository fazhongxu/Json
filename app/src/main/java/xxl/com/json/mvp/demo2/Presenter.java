package xxl.com.json.mvp.demo2;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;

/**
 * Created by xxl on 2017/12/7.
 * P 层 需要持有M层和V层对象
 * 耦合 耦合M 和 V 让V能够显示出M层的数据
 */

public class Presenter {
    private Model mModel;
    private View mView;

    public Presenter() {
        mModel = new Model();
    }

    /**
     * 请求
     */
    public void request(final Context context) {
        if (mView != null) {
            mView.loading();
        }
        mModel.request(context)
                .execute(new HttpCallBackEngine() {
                    @Override
                    public void onSuccess(final String result) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mView != null) {
                                    mView.requestSucess(result);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mView != null) {
                                    mView.requestFialure("加载出错");
                                }
                            }
                        });
                    }

                    @Override
                    public void onPreExcute(Context context, Map<String, Object> params) {

                    }
                });
    }

    /**
     * 绑定View 在Activity创建的时候绑定
     */
    public void attachView(View view) {
        this.mView = view;
    }

    /**
     * 解绑View  为了让Activity在销毁的时候不在刷新绑定数据 让activity不被一直引用导致内存泄漏
     * 在Activity销毁的时候解绑
     */
    public void detach() {
        this.mView = null;
    }

    /**
     * 取消网络请求 activity销毁的时候不需要网络请求
     */
    public void cancelRequest() {

    }
}
