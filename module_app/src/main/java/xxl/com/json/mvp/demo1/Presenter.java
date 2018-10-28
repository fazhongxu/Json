package xxl.com.json.mvp.demo1;

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

    public Presenter(View view) {
        mModel = new Model();
        this.mView = view;
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
}
