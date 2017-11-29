package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/29.
 */

public class SlidingView extends HorizontalScrollView {
    private int mRightMargin;//菜单页展开时距离右边的宽度
    private int mMenuWidth;
    private View mMenuView;
    private ViewGroup mContainer;
    private View mContentView;
    private Context mContext;
    private ImageView mShadowImageView;
    private GestureDetector mGestureDetector;
    private boolean mMenuIsOpen = false;
    private boolean mIsIntercept = false;

    public SlidingView(Context context) {
        this(context, null);
    }

    public SlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingView);

        mRightMargin = (int) typedArray.getDimension(R.styleable.SlidingView_rightWidth, dp2px(50));

        mMenuWidth = getScreenWidth(getContext()) - mRightMargin;

        typedArray.recycle();

        mGestureDetector = new GestureDetector(context, mOnGestureListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定菜单页和内容页的宽度

        //获取SlidingView的子View -- > ViewGroup容器
        mContainer = (LinearLayout) getChildAt(0);
        mMenuView = mContainer.getChildAt(0);//获取容器里面的菜单布局
        mContentView = mContainer.getChildAt(1);//获取容器里面的内容布局

        mContainer.removeView(mContentView);//移除原来的内容布局，添加新的布局进入，再把原来的布局添加到新布局中，把新布局添加到ViewGroup容器中
        // 新的布局包括两个部分，1显示内容的内容布局，2 遮盖在内容布局上面的ImageView

        FrameLayout reallyContentView = new FrameLayout(mContext);//真实的内容布局，布局里面的内容布局已经被移除了
        //指定新内容页宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        reallyContentView.setLayoutParams(layoutParams);
        reallyContentView.addView(mContentView);//把原来的布局添加到新创建的布局中

        mShadowImageView = new ImageView(mContext);
        //设置遮盖的默认颜色
        mShadowImageView.setBackgroundColor(Color.BLACK);

        //添加阴影遮罩到新布局中
        reallyContentView.addView(mShadowImageView);
        mContainer.addView(reallyContentView);//添加新布局

        ViewGroup.LayoutParams menuLp = mMenuView.getLayoutParams();//指定菜单页宽度
        menuLp.width = mMenuWidth;
        mMenuView.setLayoutParams(menuLp);

        ViewGroup.LayoutParams reallyContentParams = reallyContentView.getLayoutParams();//指定内容页宽度
        reallyContentParams.width = getScreenWidth(getContext());
        reallyContentView.setLayoutParams(reallyContentParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始滑动位置
        setScrollX(mMenuWidth);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //滑动状态改变的时候调用
        //滑动时右侧内容页透明度逐渐变化 变化范围 不透明 - 黑色遮罩
        float alpha = 1F * l / mMenuWidth;//变化梯度  变化范围 1 - 0

        //遮罩阴影变化范围 ，完全透明 - 半透明 （漏出真正的内容页）
        //1完全不透明  0完全透明
        //设置变化范围 0 - 0.6 完全透明到半透明（遮罩逐渐变暗）
        if (alpha < 0.6F) {
            alpha = 0.6F;
        }
        float alphaValue = 1 - alpha;
        mShadowImageView.setAlpha(alphaValue);//给遮罩的View设置透明度

        //左侧菜单页平移
        ViewCompat.setTranslationX(mMenuView, l * 0.6F);
    }
    //事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsIntercept = false;
        //如果菜单处于打开状态，点击内容页需要拦截事件，不能让内容页的控件响应点击事件，并且点击内容页关闭菜单
        if (ev.getX() > mMenuWidth) {
            closeMenu();
            mIsIntercept = true;
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsIntercept){//如果需要拦截，不需要再继续执行了，自己拦截的时候onTouchEvent会执行，上面已经关闭菜单了，这里就不需要做任何操作了，不要继续往下执行
            return true;
        }
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mMenuWidth / 2) {
                    //关闭菜单
                    closeMenu();
                } else {
                    openMenu();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        setScrollX(mMenuWidth);
        mMenuIsOpen = false;
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
        setScrollX(0);
        mMenuIsOpen = true;
    }

    private GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {//处理快速滑动
            //如果菜单处于打开状态，并且是向左快速滑动，则关闭菜单
//            Log.e("aaa", "onFling: " + velocityX);
            if (mMenuIsOpen) {//velocityX < 0 表示是向左快速滑动
                if (velocityX < 0) {
                    closeMenu();
                    return true;
                }
            } else {
                if (velocityX > 0) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
