package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/1.
 */

public class CustomTextView extends View {

    private String mCustomText;
    private int mCustomTextSize = 15;
    private int mCustomTextColor = Color.BLACK;

    private Paint mPaint;

    public CustomTextView(Context context) {
        this(context,null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        mCustomText = typedArray.getString(R.styleable.CustomTextView_customText);
        mCustomTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_customTextSize,sp2px(mCustomTextSize));
        mCustomTextColor = typedArray.getColor(R.styleable.CustomTextView_customTextColor,mCustomTextColor);
        //回收
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mCustomTextSize);
        mPaint.setColor(mCustomTextColor);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //MeasureSpec.EXACTLY模式 精确值，设置固定宽高就能获取得到，不需要计算
        //MeasureSpec.AT_MOST模式 wrap_content时候的模式，宽高需要计算获取
        if (MeasureSpec.AT_MOST == widthMode) {//wrap_content
            //文字是通过画笔绘制的，可通过画笔来测量
            Rect bounds = new Rect();
            //获取字体大小
            mPaint.getTextBounds(mCustomText,0,mCustomText.length(),bounds);
            width = bounds.width() ;
        }

        if (MeasureSpec.AT_MOST == heightMode) {
            Rect bounds = new Rect();
            mPaint.getTextBounds(mCustomText,0,mCustomText.length(),bounds);
            height = bounds.height();
        }
        setMeasuredDimension(width+ getPaddingLeft() + getPaddingRight(),height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(mCustomText,getPaddingLeft(),baseLine,mPaint);
    }
}
