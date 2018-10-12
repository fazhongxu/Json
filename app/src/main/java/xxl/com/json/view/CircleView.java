package xxl.com.json.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/12/4.
 */

public class CircleView extends View {
    private Paint mPaint;
    private int mCenterX,mCenterY,mCenter;
    private int mCurrentColor;
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = getHeight() /2;
        mCenterY = getWidth() / 2;
        mCenter = getWidth() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX,mCenterY,mCenter,mPaint);
    }

    /**
     * 切换图形颜色
     */
    public void changeColor(int color){
        mPaint.setColor(color);
        mCurrentColor = color;
        invalidate();
    }

    /**
     * 获取当前圆的颜色
     */
    public int getCurrentColor() {
        return mCurrentColor;
    }
}
