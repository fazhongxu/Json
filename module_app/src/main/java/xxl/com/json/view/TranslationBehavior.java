package xxl.com.json.view;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/12/2.
 */

public class TranslationBehavior extends FloatingActionButton.Behavior {
    public TranslationBehavior(Context context, AttributeSet attrs) {//必须重写构造，CoordinatorLayout 里面通过反射获取属性了，否则获取不到会报错
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        //只关注垂直滚动
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    private boolean mIsHide = false;
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //Log.e("aaa", "onNestedScroll: "+dyConsumed+""+dxConsumed);
        View view = coordinatorLayout.findViewById(R.id.ll_bottom_bar);//获取底部导航栏View
        //Log.e("aaa", "onNestedScroll: "+view);
        if (dyConsumed > 0){//正值 往上滑动  ---> 向下隐藏 FloatingActionButton
            //如果不是隐藏，就让其隐藏
            if (!mIsHide) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                int bottomMargin = lp.bottomMargin;
                child.animate().translationY(bottomMargin + child.getMeasuredHeight()).setDuration(500).start();

                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                int bottomViewMargin = layoutParams.bottomMargin;
                view.animate().translationY(bottomViewMargin+view.getMeasuredHeight()).setDuration(500).start();
                mIsHide = true;
            }
        }else {
            if (mIsHide) {
                //出现
                child.animate().translationY(0).setDuration(500).start();
                view.animate().translationY(0).start();

                mIsHide = false;

            }
        }
    }
}
