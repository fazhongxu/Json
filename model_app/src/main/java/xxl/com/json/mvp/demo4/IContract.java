package xxl.com.json.mvp.demo4;

import android.content.Context;

import xxl.com.baselibray.http.HttpUtil;
import xxl.com.json.mvp.demo4.base.BaseView;

/**
 * Created by xxl on 2018/1/2.
 */

public interface IContract {
    interface Presenter {
        void getMessage(Context context);
    }

    interface View extends BaseView {
        void loading();

        void onSuccess(String result);

        void onFialure(Exception e);
    }

    interface Model {
        HttpUtil request(Context context);
    }
}
