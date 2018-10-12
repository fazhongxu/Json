package xxl.com.json.commontoolbar;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xxl on 2018/1/28.
 * Email :
 * Description :
 */

public class CommonToolbar extends Toolbar {
    //toolbar中的三个容器布局
    private LinearLayout mLeftContainer;
    private LinearLayout mRightContainer;
    private LinearLayout mCenterContainer;
    //用来存放添加到mLeftContainer mRightContainer mCenterContainer里面的控件集合
    private SparseArray<View> mViews = new SparseArray<>();
    private TextView mTitleView;

    public CommonToolbar(Context context) {
        this(context, null);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        removeAllViews();
        //leftContainer
        mLeftContainer = new LinearLayout(getContext());
        LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                getActionBarheight());
//        mLeftContainer.setBackgroundColor(Color.GREEN);
        mLeftContainer.setGravity(Gravity.CENTER_VERTICAL);
        mLeftContainer.setPadding(dip2px(5), 0, dip2px(5), 0);
        leftParams.gravity = Gravity.START | Gravity.START;
        mLeftContainer.setLayoutParams(leftParams);
        addView(mLeftContainer);
        //rightContainer
        mRightContainer = new LinearLayout(getContext());
        LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                getActionBarheight());
        mRightContainer.setGravity(Gravity.CENTER_VERTICAL);
        rightParams.gravity = Gravity.RIGHT | Gravity.END;
        mRightContainer.setPadding(dip2px(5), 0, dip2px(5), 0);
        mRightContainer.setLayoutParams(rightParams);
        addView(mRightContainer);
        //centerContainer
        mCenterContainer = new LinearLayout(getContext());
        LayoutParams centerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                getActionBarheight());
        centerParams.gravity = Gravity.CENTER;
        mCenterContainer.setGravity(Gravity.CENTER_VERTICAL);
        mCenterContainer.setLayoutParams(centerParams);
        addView(mCenterContainer);
    }

    /**
     * 获取ActionBar的高度
     *
     * @return
     */
    private int getActionBarheight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getContext().getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private int dip2px(int dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getResources().getDisplayMetrics());
    }

    @Override
    public void setTitle(int resId) {
        super.setTitle(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.setTitle(title,14);
    }

    public void setTitle(CharSequence title,int textSize){
        if (mTitleView == null) {
            mTitleView = initTitle(title,textSize);
        }
    }

    private TextView initTitle(CharSequence title,int textSize) {
        mTitleView = new TextView(getContext());
        mTitleView.setText(title);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        mCenterContainer.addView(mTitleView);
        return mTitleView;
    }

    public void addLeftIcon(int tag, @DrawableRes int drawableRes, OnClickListener listener) {
        this.addLeftIcon(tag, drawableRes, -1, -1, listener);
    }

    /**
     * 添加左边图标
     *
     * @param tag         用来标记控件 可以通过这个标记获取控件
     * @param drawableRes 资源id
     * @param width       图标宽度
     * @param height      图标高度
     * @param listener    点击事件
     */
    public void addLeftIcon(int tag, @DrawableRes int drawableRes, /*dip*/int width, /*dip*/int height, OnClickListener listener) {
        ensure(tag);
        ImageView imageView = new ImageView(getContext());
        int imageWidth = width == -1 ? (int) (getActionBarheight() * 0.4) : dip2px(width);
        imageView.setPadding(dip2px(5),0,dip2px(5),0);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(imageWidth + imageView.getPaddingLeft() + imageView.getPaddingRight(),
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
//        imageView.setBackgroundColor(Color.RED);
        //扩大imageView点击范围
//        int imageVerticalPadding = height == -1 ? (int) (getActionBarheight() * 0.3) : (getActionBarheight() - dip2px(height)) / 2;
//        imageView.setPadding(dip2px(5), imageVerticalPadding, dip2px(5),imageVerticalPadding);
        imageView.setImageResource(drawableRes);
        imageView.setOnClickListener(listener);
        mViews.put(tag, imageView);
        mLeftContainer.addView(imageView);
    }

    private void addRightIcon(int tag, int drawableRes, OnClickListener listener) {
        this.addRightIcon(tag, drawableRes, -1, -1, listener);
    }

    private void addRightIcon(int tag, int drawableRes, int width, int height, OnClickListener listener) {
        ensure(tag);
        ImageView imageView = new ImageView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width == -1 ? getActionBarheight() * 0.4 : dip2px(width)), (int) (height == -1 ? getActionBarheight() * 0.4 : dip2px(height)));
        imageView.setLayoutParams(params);
        imageView.setImageResource(drawableRes);
        imageView.setOnClickListener(listener);
        mViews.put(tag, imageView);
        mRightContainer.addView(imageView);

    }

    private void addLeftText(int tag, CharSequence text, int textSize, OnClickListener listener) {
        ensure(tag);
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(dip2px(5), 0, dip2px(5), 0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setOnClickListener(listener);
        mViews.put(tag, textView);
        mLeftContainer.addView(textView);
    }

    private void addRightText(int tag, CharSequence text, int textSize, OnClickListener listener) {
        ensure(tag);
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(dip2px(5), 0, dip2px(5), 0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setOnClickListener(listener);
        mViews.put(tag, textView);
        mRightContainer.addView(textView);
    }

    /**
     * 设置所有Toolbar文字颜色
     *
     * @param textColor
     */
    private void setTextColor(int textColor) {
        if (mTitleView != null){
            mTitleView.setTextColor(textColor);
        }
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            View view = mViews.valueAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(textColor);
            }
        }
    }

    /**
     * 确保传入的tag唯一
     *
     * @param tag
     */
    private void ensure(int tag) {
        if (null != mViews.get(tag)) {
            throw new IllegalArgumentException("The tag must be unique!");
        }
    }

    /**
     * 根据tag标记获取添加到mLeftContainer mRightContainer mCenterContainer里面的View控件
     *
     * @param tag
     * @param <V>
     * @return
     */
    public <V extends View> V getViewByTag(int tag) {
        View view = mViews.get(tag);
        if (null != view) {
            return (V) view;
        } else {
            throw new IllegalArgumentException("tag does not exsit!");
        }
    }

    public static class Builder {
        private Context mContext;
        private ViewGroup mContentParent;
        private CommonToolbar mCommonToolbar;

        public Builder(Context context) {
            if (context instanceof Activity) {
                mContext = context;//添加到android.R.id.content framelayout系统布局中
                mContentParent = (ViewGroup) ((Activity) context).findViewById(Window.ID_ANDROID_CONTENT);
                mCommonToolbar = new CommonToolbar(context);
            } else {
                throw new IllegalArgumentException("The Context should be of type Activity,or the content has been destroyed");
            }
        }

        public Builder(View contentView) {//自己传入ViewGroup添加toolbar
            if (contentView instanceof LinearLayout) {
                mContext = contentView.getContext();
                mContentParent = (ViewGroup) contentView;
                mCommonToolbar = new CommonToolbar(contentView.getContext());
            } else {
                throw new IllegalArgumentException("contentView must be LinearLayout!");
            }
        }

        /**
         * 设置背景色
         *
         * @param color
         * @return
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            mCommonToolbar.setBackgroundColor(color);
            return this;
        }

        public Builder setBackgroundColorResorce(@ColorRes int drawableRes) {
            mCommonToolbar.setBackgroundResource(drawableRes);
            return this;
        }

        public Builder setLeftIcon(int tag, @DrawableRes int drawableRes, OnClickListener listener) {
            mCommonToolbar.addLeftIcon(tag, drawableRes, listener);
            return this;
        }

        /**
         * 设置左边的图标
         *
         * @param tag         设置一个tag标记 设置之后可以通过标记获取控件
         * @param drawableRes
         * @param listener
         * @param width       dip类型的宽
         * @param height      dip类型的高
         * @return
         */
        public Builder setLeftIcon(int tag, @DrawableRes int drawableRes, /*dipValue*/int width,/*dipValue*/int height, OnClickListener listener) {
            mCommonToolbar.addLeftIcon(tag, drawableRes, width, height, listener);
            return this;
        }

        public Builder setLeftText(int tag, CharSequence text, OnClickListener listener) {
            mCommonToolbar.addLeftText(tag, text, 14, listener);
            return this;
        }

        /**
         * 设置左边文本
         *
         * @param tag
         * @param text
         * @param textSize
         * @param listener
         * @return
         */
        public Builder setLeftText(int tag, CharSequence text, /*dipValue*/int textSize, OnClickListener listener) {
            mCommonToolbar.addLeftText(tag, text, textSize, listener);
            return this;
        }

        public Builder setRightIcon(int tag, @DrawableRes int drawableRes, OnClickListener listener) {
            mCommonToolbar.addRightIcon(tag, drawableRes, listener);
            return this;
        }

        public Builder setRightIcon(int tag, @DrawableRes int drawableRes, /*dipValue*/int width,/*dipValue*/int height, OnClickListener listener) {
            mCommonToolbar.addRightIcon(tag, drawableRes, width, height, listener);
            return this;
        }

        public Builder setRightText(int tag, CharSequence text, OnClickListener listener) {
            mCommonToolbar.addRightText(tag, text, 14, listener);
            return this;
        }

        public Builder setRightText(int tag, CharSequence text, int textSize, OnClickListener listener) {
            mCommonToolbar.addRightText(tag, text, textSize, listener);
            return this;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            mCommonToolbar.setTextColor(textColor);
            return this;
        }

        /**
         * 设置文字颜色
         *
         * @param colorRes
         * @return
         */
        public Builder setTextColorRes(@ColorRes int colorRes) {
            mCommonToolbar.setTextColor(ContextCompat.getColor(mContext, colorRes));
            return this;
        }

        public Builder setTitleText(CharSequence title){
            mCommonToolbar.setTitle(title);
            return this;
        }

        public Builder setTitleText(CharSequence title,int textSize){
            mCommonToolbar.setTitle(title,textSize);
            return this;
        }

        public Builder setTitleText(@StringRes int resId){
            mCommonToolbar.setTitle(resId);
            return this;
        }

        public CommonToolbar apply() {
            //设置toolbar宽高
            mCommonToolbar.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mContentParent != null) {
                //把Toolbar添加到父容器中
                mContentParent.addView(mCommonToolbar, 0);
            }
            //mContentParent是toolbar和内容页的父容器
            //判断mContnetParent是否是LinearLayout布局，如果不是,则会出现内容页和Toolbar布局有重叠，
            //应该把内容页布局移动至toolbar的下方，如果是，不需要移动，因为toolbar是添加到LinearLayout的第一个位置的
            mCommonToolbar.post(new Runnable() {//测量完毕再调整布局
                @Override
                public void run() {
                    adjustLayout();
                }
            });
            return mCommonToolbar;
        }

        /**
         * 调整内容布局位置，将内容页放置到Toolbar位置的下方
         */
        private void adjustLayout() {
            if (mContentParent != null && !(mContentParent instanceof LinearLayout)) {
                //获取内容页布局  toolbar是通过Builder构造添加到第一个位置，此时内容页应该在第1个位置
                ViewGroup contentView = (ViewGroup) mContentParent.getChildAt(1);
                //内容页 marginTop 应该是Toolbar的高度
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) contentView.getLayoutParams();
                marginLayoutParams.topMargin = getNeedHeight();
                contentView.setLayoutParams(marginLayoutParams);
            }
        }

        /**
         * 获取内容页需要移动的高度 也就是 toolbar的宽度，如果有沉浸式状态栏，应该添 加 状态栏高度
         *
         * @return
         */
        private int getNeedHeight() {
            int toolbarHeight = mCommonToolbar.getHeight();
            //沉浸式
            return toolbarHeight;
        }
    }

}
