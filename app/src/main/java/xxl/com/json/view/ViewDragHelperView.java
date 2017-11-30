package xxl.com.json.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by xxl on 2017/11/30.
 */

public class ViewDragHelperView extends RelativeLayout {
    private ViewDragHelper mDragHelper;

    public ViewDragHelperView(Context context) {
        this(context, null);
    }

    public ViewDragHelperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this,mDragCallback);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //指定的View是否可以拖动 即指定的child是否可以拖动
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //垂直拖动
            Log.e("aaa", "clampViewPositionVertical: "+top+"--->"+dy );
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //水平拖动
            return left;
        }
    };
}
