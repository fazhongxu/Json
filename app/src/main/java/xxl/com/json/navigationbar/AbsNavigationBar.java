package xxl.com.json.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xxl on 2017/12/21.
 */

public class AbsNavigationBar implements INavigation {
    private Builder mBuilder;
    private View mLayoutView;

    protected AbsNavigationBar(Builder builder) {
        this.mBuilder = builder;

        createLayout();

        attatchParent(mLayoutView);

        bindParams();
    }

    /**
     * 创建布局
     */
    @Override
    public void createLayout() {
        //方式1 需要使用者传入要添加view的父容器的id
        //mLayoutView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.mlayoutId, mBuilder.mParent, false);
        //方式2 直接把要添加的view添加到android.R.id.content的第一个位置 不需要使用者传入父布局的id
        mLayoutView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.mlayoutId, null);
    }

    /**
     * 将创建的布局 添加到父容器布局中
     * 直接添加到decorView的第一个容器的第一个位置
     * 添加到decorView的第一个孩子（Linerlayout)
     * 的第一个位置上（setContentView的内容布局就是添加到decorView的linearLayout里面的 子布局的android.R.id.content 布局中的）
     */
    @Override
    public void attatchParent(View view) {
        ViewGroup decorView = (ViewGroup) ((Activity) mBuilder.mContext).getWindow().getDecorView();
        ViewGroup decorViewChild = (ViewGroup) decorView.getChildAt(0);
        decorViewChild.addView(view, 0);
    }

    /**
     * 绑定参数
     */
    @Override
    public void bindParams() {
        //遍历集合 绑定参数
        Map<Integer, CharSequence> textMap = mBuilder.mTextMap;
        for (Map.Entry<Integer, CharSequence> entrySet : textMap.entrySet()) {
            TextView tv = findViewById(entrySet.getKey());
            tv.setText(entrySet.getValue());
        }
        Map<Integer, View.OnClickListener> clickListenerMap = mBuilder.mClickLisenterMap;
        for (Map.Entry<Integer, View.OnClickListener> entrySet : clickListenerMap.entrySet()) {
            View view = findViewById(entrySet.getKey());
            view.setOnClickListener(entrySet.getValue());
        }

        Map<Integer, Boolean> visibleView = mBuilder.mVisibleMap;
        for (Map.Entry<Integer, Boolean> entrySet : visibleView.entrySet()) {
            View view = findViewById(entrySet.getKey());
            view.setVisibility(entrySet.getValue() ? View.GONE : View.VISIBLE);
        }

        Map<Integer, Integer> drawableMap = mBuilder.mDrawableMap;
        for (Map.Entry<Integer, Integer> entrySet : drawableMap.entrySet()) {
            View view = findViewById(entrySet.getKey());
            view.setBackgroundResource(entrySet.getValue());
        }
    }

    public <V extends View> V findViewById(int viewId) {
        return (V) mLayoutView.findViewById(viewId);
    }

    public abstract static class Builder<B extends Builder> {
        public Context mContext;
        public int mlayoutId;
        public Map<Integer, CharSequence> mTextMap;
        public Map<Integer, View.OnClickListener> mClickLisenterMap;
        public Map<Integer, Boolean> mVisibleMap;
        public Map<Integer, Integer> mDrawableMap;

        public Builder(Context context, int layoutId) {
            this.mContext = context;
            this.mlayoutId = layoutId;
            this.mTextMap = new LinkedHashMap<>();
            this.mClickLisenterMap = new LinkedHashMap<>();
            this.mVisibleMap = new LinkedHashMap<>();
            this.mDrawableMap = new LinkedHashMap<>();
        }

        public abstract AbsNavigationBar create();

        public B setText(int viewId, CharSequence text) {
            mTextMap.put(viewId, text);
            return (B) this;
        }

        public B setOnClickLisenter(int viewId, View.OnClickListener onClickListener) {
            mClickLisenterMap.put(viewId, onClickListener);
            return (B) this;
        }

        public B setVisible(int viewId, boolean visible) {
            mVisibleMap.put(viewId, visible);
            return (B) this;
        }

        public B setIcon(int viewId, int drawableId) {
            mDrawableMap.put(viewId, drawableId);
            return (B) this;
        }

    }
}
