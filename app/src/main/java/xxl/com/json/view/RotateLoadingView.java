package xxl.com.json.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import xxl.com.json.R;

/**
 * Created by xxl on 2018/2/5.
 */

public class RotateLoadingView extends View {

    private int[] mColors;
    private Paint mPaint;
    private Float mCurrentRotateAngle;
    private int mRotateRadius;
    private boolean mInit = true;

    public RotateLoadingView(Context context) {
        this(context,null);
    }

    public RotateLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取颜色数组
        mColors = getResources().getIntArray(R.array.load_view_color);

        ValueAnimator objectAnimator = ObjectAnimator.ofFloat(0, (float) (Math.PI * 2));
        objectAnimator.setRepeatCount(-1);
        objectAnimator.start();
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRotateAngle = (Float) animation.getAnimatedValue();
                Log.e("aaa", "onAnimationUpdate: "+mCurrentRotateAngle);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mInit){
            init();
            mInit = false;
        }

        for (int i = 0; i < mColors.length - 1; i++) {

            mPaint.setColor(mColors[i]);
//            canvas.drawCircle(mCurrentRotateAngle + ,,mRotateRadius / 8,mPaint);
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRotateRadius = getMeasuredWidth() / 2;
        int count = mColors.length;


    }
}
