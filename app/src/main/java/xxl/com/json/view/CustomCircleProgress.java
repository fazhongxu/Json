package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/3.
 */

public class CustomCircleProgress extends View {
    private int mOuterSize;
    private int mInnerSize;
    private int mOuterColor;
    private int mInnerColor;

    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    private int mMaxProgress;
    private int mCurrentProgress;


    public CustomCircleProgress(Context context) {
        this(context, null);
    }

    public CustomCircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
        initPaint();
    }

    /**
     * 初始化自定义属性
     */
    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleProgress);
        mOuterSize = (int) typedArray.getDimension(R.styleable.CustomCircleProgress_outerBorderStroke, mOuterSize);
        mInnerSize = (int) typedArray.getDimension(R.styleable.CustomCircleProgress_innerBorderStroke, mInnerSize);
        mOuterColor = typedArray.getColor(R.styleable.CustomCircleProgress_outerBorderColor, mOuterColor);
        mInnerColor = typedArray.getColor(R.styleable.CustomCircleProgress_innerBorderColor, mInnerColor);

        //文字
        mTextSize = typedArray.getDimensionPixelOffset(R.styleable.CustomCircleProgress_circleProgressTextSize, sp2px(mTextSize));
        mTextColor = typedArray.getColor(R.styleable.CustomCircleProgress_circleProgressTextColor, mTextColor);

        typedArray.recycle();
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mOuterPaint = getPaintByColor(mOuterColor, mOuterSize);
        mInnerPaint = getPaintByColor(mInnerColor, mInnerSize);

        mTextPaint = getPaintByColor(mTextColor, 0);
        mTextPaint.setTextSize(mTextSize);
    }

    /**
     * 根据颜色获取对应颜色样式的画笔
     */
    private Paint getPaintByColor(int color, int borderSize) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(borderSize);
        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔带圆角
        paint.setStyle(Paint.Style.STROKE);//设置画笔样式，只画边框
        return paint;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = Math.min(widthSize, heightSize);
        setMeasuredDimension(width, width);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制外圆  圆心坐标float cx, float cy, float radius 半径
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - mOuterSize, mOuterPaint);

        //绘制内圆,内圆是圆弧的形式逐渐绘制完成的
        float sweepAngle = (mCurrentProgress *1.0F / mMaxProgress) * 360;

        canvas.drawArc(mInnerSize, mInnerSize, getWidth() - mInnerSize, getWidth() - mInnerSize, 0, sweepAngle, false, mInnerPaint);

        //绘制文字
        Paint.FontMetrics fontMetrics = mInnerPaint.getFontMetrics();
        int dx = getWidth() / 2 - mInnerSize;
        //基线
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;

        int progress = (int) sweepAngle;
        String text = progress + "";
        canvas.drawText(text, 0, text.length(), dx, baseLine, mTextPaint);
    }

    /**
     * 设置最大进度
     */
    public void setMaxProgress(int progress) {
        this.mMaxProgress = progress;
    }

    /**
     * 设置进度
     */
    public void setProgress(int progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }
}

