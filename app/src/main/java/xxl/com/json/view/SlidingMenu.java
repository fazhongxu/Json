package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/19.
 */

public class SlidingMenu extends HorizontalScrollView {
    private int mRightMargin;//菜单页展开时距离右边的宽度
    private int mMenuWidth;
    private View mMenuView;
    private View mContentView;

    private GestureDetector mGestureDetector;//手势处理类
    private boolean mMenuIsOpen = false;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);

        mRightMargin = (int) typedArray.getDimension(R.styleable.SlidingMenu_rightMargin, dp2px(50));

        mMenuWidth = getScreenWidth(getContext()) - mRightMargin;

        typedArray.recycle();

        mGestureDetector = new GestureDetector(context, mGestureListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定菜单页和内容页的宽度
        //获取slidingmenu里面的布局
        ViewGroup viewGroup = (LinearLayout) getChildAt(0);
        mMenuView = viewGroup.getChildAt(0);
        mContentView = viewGroup.getChildAt(1);

        ViewGroup.LayoutParams menuLp = mMenuView.getLayoutParams();
        menuLp.width = mMenuWidth;
        mMenuView.setLayoutParams(menuLp);

        ViewGroup.LayoutParams contentLp = mContentView.getLayoutParams();
        contentLp.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentLp);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始滑动位置
        setScrollX(mMenuWidth);
    }

    //手指抬起自动滑动到距离近的一边
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mGestureDetector.onTouchEvent(ev)) {//必须在onTouchEvent里面调用
            //如果是快速滑动，下面就不要执行了
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX < mMenuWidth / 2) {
                    //菜单打开
                    openMenu();
                } else {
                    //菜单关闭
                    closeMenu();
                }
                return true;//确保 super.onTouchEvent(ev) 不会执行。
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 菜单打开
     */
    private void openMenu() {
        smoothScrollTo(0, 0);
        mMenuIsOpen = true;
    }

    /**
     * 菜单关闭
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1f * l / mMenuWidth;// 1 - 0
        //内容页缩放
        //1 - 0.7
        float scaleValue = 0.7f + 0.3f * scale;
        ViewCompat.setScaleY(mContentView, scaleValue);//默认是以中心点缩放
        ViewCompat.setScaleX(mContentView, scaleValue);
        ViewCompat.setPivotX(mContentView, 0);//设置以内容页的左边的边的中心作为缩放点缩放
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);

        //菜单页透明度改变
        // 1 - 0.3
        float alpha = 0.7f * (1 - scale) + 0.3f;
        ViewCompat.setAlpha(mMenuView, alpha);

        //菜单页平移
        ViewCompat.setTranslationX(mMenuView, l * 0.1f);
    }

    /**
     * 手势处理监听
     */
    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        //处理快速滑动
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //Log.e("aaa", "onFling: "+velocityX);
            //快速滑动就会条用onFling
            //快速向左滑动 velocityX 为负值  快速向右滑动 velocityX为正值
            if (mMenuIsOpen) {//如果是打开，快速滑动就关闭菜单
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
