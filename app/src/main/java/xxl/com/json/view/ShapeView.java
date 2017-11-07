package xxl.com.json.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/11/6.
 */

public class ShapeView extends View {

    private Paint mPaint;
    private Path mPath;
    private Shape mShape = Shape.CIRCLE;

    public enum Shape {
        CIRCLE, SQUARE, TRIANGLE
    }

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mShape) {
            case CIRCLE:
                //画圆
                mPaint.setColor(Color.GREEN);
                int center = getWidth() / 2;
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case SQUARE:
                //画正方形
                mPaint.setColor(Color.RED);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case TRIANGLE:
                //画三角形
                mPaint.setColor(Color.YELLOW);
                if (mPath == null) {
                    mPath = new Path();
                }
                mPath.moveTo(getWidth() / 2, 0);
                mPath.lineTo(0, (float) ((getHeight() / 2) * Math.sqrt(3.0D)));
                mPath.lineTo(getWidth(), (float) ((getHeight() / 2) * Math.sqrt(3.0D)));
                mPath.close();//闭合
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    /**
     * 改变图形形状
     */
    public void changeShape() {
        switch (mShape) {
            case CIRCLE://如果是圆形，就改变绘制正方形
                mShape = Shape.SQUARE;
                break;
            case SQUARE://如果是正方形，就改变绘制三角形
                mShape = Shape.TRIANGLE;
                break;
            case TRIANGLE:
                mShape = Shape.CIRCLE;
                break;
            //不断重绘
        }
        invalidate();
    }
}
