package xxl.com.json.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by xxl on 2017/11/8.
 */

public class SlideBar extends View {

    private String[] mLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint;
    private String mCurrentPositionLetter;

    public SlideBar(Context context) {
        this(context, null);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(15));
        mPaint.setColor(Color.GREEN);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec) + getPaddingTop() + getPaddingBottom();
        //宽度需要计算，宽度等于文字宽度+paddingleft+paddingRight
        int width = (int) (mPaint.measureText("A") + getPaddingLeft() + getPaddingRight());
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLetters.length; i++) {
            //画字母
            //每个字母的高度
            int letterHeight = (getHeight()) / mLetters.length;
            //字母的宽度
            int letterWidth = (int) mPaint.measureText(mLetters[i]);
            //控件宽度
            int width = letterWidth + getPaddingLeft() + getPaddingRight();
            int x = width / 2 - letterWidth / 2;
            //每个字母的中心
            int letterCenterY = i * letterHeight + letterHeight / 2;
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            //基线
            int baseLine = letterCenterY + dy;
            if (mLetters[i].equals(mCurrentPositionLetter)) {//点亮当前位置的字母
                mPaint.setColor(Color.RED);
                if (onTouchLisener != null){
                    onTouchLisener.onTouch(mLetters[i]);
                }
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            } else {
                mPaint.setColor(Color.GREEN);
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //触摸到的文字点亮
                //根据触摸到的点计算出文字位置
                float currentY = event.getY();
                int letterHeight = getHeight() / mLetters.length;
                int currentPosition = (int) (currentY / letterHeight);

                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > mLetters.length) {
                    currentPosition = mLetters.length;
                }
                //获取当前位置的字母
                mCurrentPositionLetter = mLetters[currentPosition];
                invalidate();//重绘
                break;
        }
        return true;
    }
    private OnTouchLisenter onTouchLisener;

    public void setOnTouchLisenter(OnTouchLisenter onTouchLisener){
        this.onTouchLisener = onTouchLisener;
    }

    public interface OnTouchLisenter{
        void onTouch(String letter);
    }
}
