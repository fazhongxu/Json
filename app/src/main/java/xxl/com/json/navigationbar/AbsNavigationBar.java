package xxl.com.json.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by xxl on 2017/12/20.
 */

public class AbsNavigationBar implements INavigation {
    private Builder mBuilder;
    private View mLayoutView;

    protected AbsNavigationBar(Builder builder) {
        this.mBuilder = builder;

        createLayout();

        ViewGroup contentParent = (ViewGroup) ((Activity) mBuilder.mContext).findViewById(android.R.id.content);

        attatchParent(mLayoutView,contentParent);

        bindParams();
    }


    /**
     * 创建布局
     */
    @Override
    public void createLayout() {
        ViewGroup contentParent = (ViewGroup) ((Activity) mBuilder.mContext).findViewById(android.R.id.content);

        mLayoutView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.mLayoutId, contentParent, false);
    }

    /**
     * 添加到父布局中
     */
    @Override
    public void attatchParent(View layoutView, ViewGroup parent) {
        //添加到父布局的第一个位置
        parent.addView(layoutView,0);
    }

    /**
     * 绑定参数
     */
    @Override
    public void bindParams() {

    }

    public abstract static class Builder {
        public Context mContext;
        public ViewGroup mParent;
        public int mLayoutId;

        public Builder(Context context, ViewGroup parent, int layoutId) {
            this.mContext = context;
            this.mParent = parent;
            this.mLayoutId = layoutId;
        }

        protected abstract AbsNavigationBar create();
    }
}
