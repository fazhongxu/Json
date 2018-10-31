package xxl.com.baselibray.http.retrofit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xxl on 2018/10/31.
 * <p>
 * Description :
 */

public abstract class NetObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        HttpThrowable httpThrowable = HttpThrowable.handleThrowable(e);
        onError(httpThrowable);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(HttpThrowable e);
}
