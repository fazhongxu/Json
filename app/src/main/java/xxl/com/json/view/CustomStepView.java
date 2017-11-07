package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import xxl.com.json.R;


/**
 * Created by xxl on 2017/11/2.
 */

public class CustomStepView extends View {
    private int mOuterBorderSize;
    private int mInnerBorderSize;
    private int mOuterBorderColor;
    private int mInnerBorderColor;
    private int mStepTextSize;
    private int mStepTextColor;
    private int mMaxStep;
    private int mProgress;

    private Paint mOuterPaint, mInnerPaint, mTextPaint;

    public CustomStepView(Context context) {
        this(context, null);
    }

    public CustomStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomStepView);
        mOuterBorderSize = (int) typedArray.getDimension(R.styleable.CustomStepView_outerBorderSize, mOuterBorderSize);
        mInnerBorderSize = (int) typedArray.getDimension(R.styleable.CustomStepView_innerBorderSize, mInnerBorderSize);

        mOuterBorderColor = typedArray.getColor(R.styleable.CustomStepView_outerColor, mOuterBorderColor);
        mInnerBorderColor = typedArray.getColor(R.styleable.CustomStepView_innerColor, mInnerBorderColor);

        mStepTextColor = typedArray.getColor(R.styleable.CustomStepView_stepTextColor, mStepTextColor);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomStepView_stepTextSize, mInnerBorderSize);
        typedArray.recycle();

        //创建画笔
        //外圆圆弧画笔
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBorderColor);
        mOuterPaint.setStrokeWidth(mOuterBorderSize);
        //设置画笔样式
        mOuterPaint.setStyle(Paint.Style.STROKE);
        //设置圆角
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        //内圆圆弧画笔
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBorderColor);
        mInnerPaint.setStrokeWidth(mInnerBorderSize);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int minWidth = Math.min(width, height);
        setMeasuredDimension(minWidth, minWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制外圆圆弧
        canvas.drawArc(mOuterBorderSize, mOuterBorderSize, getWidth() - mOuterBorderSize, getHeight() - mOuterBorderSize, 135, 270, false, mOuterPaint);

        //绘制外圆圆弧 弧度 百分比
        if (mMaxStep == 0) return;
        float sweepAngle = ((float) mProgress / mMaxStep) * 270;
        canvas.drawArc(mInnerBorderSize, mInnerBorderSize, getWidth() - mInnerBorderSize, getHeight() - mInnerBorderSize, 135, sweepAngle, false, mInnerPaint);

        //绘制文字
        String text = "" + mProgress;

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float bottom = fontMetrics.bottom;
        float top = fontMetrics.top;

        int dy = (int) ((bottom - top) / 2 - bottom);
        //基准线
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(text, 0, text.length(), getWidth() / 2 - text.length() / 2 - mInnerBorderSize, baseLine, mTextPaint);

    }

    /**
     * 设置最大步数
     */
    public void setMaxStep(int step) {
        this.mMaxStep = step;
    }

    /**
     * 设置进度值
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }
}
