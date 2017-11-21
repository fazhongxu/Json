package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
    private int mContentViewWidth;//内容页的宽度
    private int mMenuWidth;
    private View mMenuView;
    private View mContentView;

    public SlidingMenu(Context context) {
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);

        mContentViewWidth = (int) typedArray.getDimension(R.styleable.SlidingMenu_contentViewWidth,dp2px(50));

        mMenuWidth = getScreenWidth(getContext()) - mContentViewWidth;
        typedArray.recycle();
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

    private int getScreenWidth (Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels;
    }

    private int dp2px(int dpValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,getResources().getDisplayMetrics());
    }

}
