package xxl.com.json.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/11/8.
 */

public class SlideBar extends View {

    private String[] mLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint;

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
        float textSize = mPaint.measureText("A");
        mPaint.setTextSize(textSize);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //宽度需要计算，宽度等于文字宽度+paddingleft+paddingRight
        int width = (int) (mPaint.measureText("A") + getPaddingLeft() + getPaddingRight());
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLetters.length; i++) {

        }
    }
}
