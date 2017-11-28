package xxl.com.json.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xxl on 2017/11/28.
 */

public class TouchView extends View {
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("View", "View dispatchTouchEvent:  -->"+event.getAction());
        return super.dispatchTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("View", "View onTouchEvent: -->"+event.getAction());
        return super.onTouchEvent(event);
    }
}
