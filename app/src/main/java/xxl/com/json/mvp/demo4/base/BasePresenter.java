package xxl.com.json.mvp.demo4.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xxl on 2018/1/2.
 */

public class BasePresenter<V extends BaseView> {
    private V mView;
    private V mProxyView;

    /**
     * 绑定View
     *
     * @param view
     */
    void attachView(V view) {
        this.mView = view;

        //动态代理 判断view是否已经解绑 不用在每个子类都判断view是否为空 在此统一判断
        mProxyView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //统一判断View是否为空
                        if (mView == null) {
                            return null;
                        }
                        return method.invoke(mView, args);
                    }
                });
    }

    /**
     * 解绑View
     */
    void detachView() {
        this.mView = null;
    }

    public V getView() {
        //return mView;
        //返回经过动态代理判空之后的View
        return mProxyView;
    }
}
