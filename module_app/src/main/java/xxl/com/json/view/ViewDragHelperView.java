package xxl.com.json.view;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

/**
 * Created by xxl on 2017/11/30.
 */

public class ViewDragHelperView extends RelativeLayout {

    private ViewDragHelper mDragHelper;
    private View mContentView;
    private View mMenu;
    private int mMenuHeight;
    private boolean mMenuIsOpen = false;

    public ViewDragHelperView(Context context) {
        this(context, null);
    }

    public ViewDragHelperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, mHelperCallBack);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }
    private float mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //菜单打开的情况下 拦截recyclerView事件，不要让recyclerView响应触摸事件
        if (mMenuIsOpen){
            return true;
        }
        //recyclerView调用的请求父类不要拦截事件，也就是请求本View不要拦截事件，
        //所以本类的onTouchEvent事件无法响应
        //需求，向下拖动不要拦截
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mDragHelper.processTouchEvent(ev);//拿到完整的事件
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if (moveY - mDownY > 0 && !canChildScrollUp()){
                    //向下滑动 && 第一个条目可见（不能向下滚动了，拦截recyvlerView的onTouch事件
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    // SwipeRefreshLayout 源码里面的方法 判断是否还可以向下滚动
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mContentView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mContentView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mContentView, -1) || mContentView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mContentView, -1);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("ViewDragHelperView 只允许有两个子View");
        }
        mContentView = getChildAt(1);//内容View
        mMenu = getChildAt(0);//底部的菜单
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //获取菜单的高度
        mMenuHeight = mMenu.getHeight();
    }

    private ViewDragHelper.Callback mHelperCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //允许拖动的View
            return mContentView == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //垂直方向移动
            //限制内容页 拖动的范围 0 - 菜单的高度
            if (top <= 0){
                top = 0;
            }
            if (top >= mMenuHeight){
                top = mMenuHeight;
            }
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //手指放开，放开时候的内容页的顶部坐标 <= 菜单页高度的一半，移动内容页到0的位置（关闭）
            if (mContentView.getTop() <= mMenuHeight /2) {
                mDragHelper.settleCapturedViewAt(0,0);
                mMenuIsOpen = false;
            }else {
                mDragHelper.settleCapturedViewAt(0,mMenuHeight);
                mMenuIsOpen = true;
            }
            invalidate();
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)){
            invalidate();
        }
    }
}
