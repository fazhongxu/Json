package xxl.com.json.mvp.demo3.base;

/**
 * Created by xxl on 2017/12/11.
 */

public abstract class AbsMvpBasePresenter<V extends IBaseView> {
    //绑定 解绑 获取View
    private V mView;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detachView(){
        this.mView = null;
    }

    public V getView() {
        return mView;
    }
}
