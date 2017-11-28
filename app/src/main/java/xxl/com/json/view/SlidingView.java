package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/29.
 */

public class SlidingView extends HorizontalScrollView {
    private int mRightMargin;//菜单页展开时距离右边的宽度
    private int mMenuWidth;
    private View mMenuView;
    private View mContentView;

    public SlidingView(Context context) {
        this(context, null);
    }

    public SlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingView);

        mRightMargin = (int) typedArray.getDimension(R.styleable.SlidingView_rightWidth, dp2px(50));

        mMenuWidth = getScreenWidth(getContext()) - mRightMargin;

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定菜单页和内容页的宽度
        //获取slidingview里面的布局
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:

        }
        return super.onTouchEvent(ev);
    }

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
