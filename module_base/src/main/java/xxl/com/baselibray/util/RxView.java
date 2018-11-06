package xxl.com.baselibray.util;

import android.view.View;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xxl on 2018/11/6.
 * <p>
 * Description : 防止多次点击工具类
 */

public class RxView {

    /**
     * View防止多次点击
     * @param action
     * @param time
     * @param views
     */
    public static void onClick(Action<View> action,long time,View...views) {
        for (View view : views) {
            Disposable disposable = onClick(view)
                    .throttleFirst(time, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<View>() {
                        @Override
                        public void accept(View view) throws Exception {
                            action.onClick(view);
                        }
                    });
        }
    }

    /**
     * View防止多次点击 单个按钮
     * @param view
     * @return
     */
    public static Observable<View> onClick(@NonNull View view) {
        Util.checkNotNull(view, "view is null");
        return Observable.create(new ViewClickObservableOnSubscribe(view));
    }

     static class ViewClickObservableOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        ViewClickObservableOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(ObservableEmitter<View> emitter) throws Exception {

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(view);
                    }
                }
            };
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     * A one-argument action. 点击事件转发接口
     *
     * @param <V> the first argument type
     */
    public interface Action<V> {
        void onClick(V v);
    }

}
