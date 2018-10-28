package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;

import xxl.com.json.R;

/**
 * Created by xxl on 2018/1/16.
 */

public class CustomPasswordEditText extends AppCompatEditText {

    private int mPasswordNum = 6;//密码个数
    private int mBorderRadius = 4;//密码边框圆角大小
    private int mBorderColor = Color.parseColor("#D1D2D6");//密码边框颜色
    private int mBorderSize;//密码边框颜色
    private int mPasswordDotRadius;//密码点半径大小
    private int mPasswordDotColor;//密码点的颜色
    private int mSplitLineColor;//分割线颜色
    private int mSplitLineSize;//分割线大小

    private int mPasswordItemWidth;

    private Paint mPaint;


    public CustomPasswordEditText(Context context) {
        this(context, null);
    }

    public CustomPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context, attrs);

        initPaint();

        //设置输入类型为密码
        setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //设置输入的最大长度
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(mPasswordNum)});

        //禁止软键盘弹出 自定义键盘输入

    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomPasswordEditText);

        mPasswordNum = typedArray.getInt(R.styleable.CustomPasswordEditText_passwordNum, mPasswordNum);
        mBorderRadius = (int) typedArray.getDimension(R.styleable.CustomPasswordEditText_borderRadius, dip2px(mBorderRadius));
        mBorderSize = (int) typedArray.getDimension(R.styleable.CustomPasswordEditText_borderSize, dip2px(mBorderSize));
        mBorderColor = typedArray.getColor(R.styleable.CustomPasswordEditText_borderColor, mBorderColor);
        mPasswordDotRadius = (int) typedArray.getDimension(R.styleable.CustomPasswordEditText_passwordDotRadius, dip2px(mPasswordDotRadius));
        mPasswordDotColor = typedArray.getColor(R.styleable.CustomPasswordEditText_passwordDotColor, mPasswordDotColor);
        mSplitLineColor = typedArray.getColor(R.styleable.CustomPasswordEditText_splitLineColor, mSplitLineColor);
        mSplitLineSize = (int) typedArray.getDimension(R.styleable.CustomPasswordEditText_splitLineSize, dip2px(mSplitLineSize));

        typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //每个密码的宽度
        mPasswordItemWidth = ((getWidth() - 2 * mBorderSize) - (mPasswordNum - 1) * mSplitLineSize) / mPasswordNum;
        //绘制背景框
        drawBorder(canvas);

        //绘制分割线
        drawSplitLine(canvas);

        //绘制密码点
        drawPassword(canvas);

        if (mListener != null) {
            String password = getText().toString().trim();
            if (password.length() >= mPasswordNum) {
                mListener.getPassword(password);
            }
        }
    }

    private void drawPassword(Canvas canvas) {
        mPaint.setColor(mPasswordDotColor);
        mPaint.setStyle(Paint.Style.FILL);
        int length = getText().length();
        float centerX;
        float centerY = getHeight() / 2;
        for (int i = 0; i < length; i++) {
            centerX = mBorderSize + mPasswordItemWidth / 2 + i * mPasswordItemWidth + i * mSplitLineSize;
            canvas.drawCircle(centerX, centerY, mPasswordDotRadius, mPaint);
        }

    }

    private void drawSplitLine(Canvas canvas) {
        mPaint.setColor(mSplitLineColor);
        mPaint.setStrokeWidth(mSplitLineSize);
        int startX;
        int startY = mBorderSize;
        int endX;
        int endY = getHeight() - mBorderSize;
        for (int i = 1; i < mPasswordNum; i++) {
            startX = i * mPasswordItemWidth + i * mSplitLineSize + mBorderSize;
            endX = startX;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
    }

    private void drawBorder(Canvas canvas) {
        //如果设置了圆角大小 绘制带圆角的矩形 否则直接绘制矩形
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderSize);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(mBorderSize, mBorderSize, getWidth() - mBorderSize, getHeight() - mBorderSize);
        if (mBorderRadius == 0) {
            canvas.drawRect(rectF, mPaint);
        } else {
            canvas.drawRoundRect(rectF, mBorderRadius, mBorderRadius, mPaint);
        }
    }


    private int dip2px(float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getResources().getDisplayMetrics());
    }

    private PassswordFullListener mListener;

    public void setOnPassswordFullListener(PassswordFullListener listener) {
        this.mListener = listener;
    }

    public interface PassswordFullListener {
        void getPassword(String password);
    }

}
