package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/7.
 */

public class RatingBar extends View {
    private int mRatingNum;
    private int mRatingNormalId;
    private int mRatingFocusId;

    private Bitmap mRatingNormalBitmap, mRatingFocusBitmap;

    private int mCurrentRatingGrade;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        mRatingNum = typedArray.getInt(R.styleable.RatingBar_ratingNum, 0);
        mRatingNormalId = typedArray.getResourceId(R.styleable.RatingBar_ratingNormal, 0);
        mRatingFocusId = typedArray.getResourceId(R.styleable.RatingBar_ratingFocus, 0);
        if (mRatingNormalId == 0) {
            throw new RuntimeException("请设置 ratingNormal 属性");
        }
        mRatingFocusId = typedArray.getResourceId(R.styleable.RatingBar_ratingFocus, 0);
        if (mRatingFocusId == 0) {
            throw new RuntimeException("请设置 ratingFocus 属性");
        }
        typedArray.recycle();


        mRatingNormalBitmap = BitmapFactory.decodeResource(getResources(), mRatingNormalId);
        mRatingFocusBitmap = BitmapFactory.decodeResource(getResources(), mRatingFocusId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mRatingNormalBitmap.getWidth() * mRatingNum +getPaddingLeft()*mRatingNum;
        int height = mRatingNormalBitmap.getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mRatingNum; i++) {//绘制默认星星
            int left = i * mRatingNormalBitmap.getWidth()+getPaddingLeft()*i;//星星数 * 星星宽度
            if (i < mCurrentRatingGrade) {//点亮mCurrentRatingGrade之前的星星
                canvas.drawBitmap(mRatingFocusBitmap, left, 0, null);
            } else {
                canvas.drawBitmap(mRatingNormalBitmap, left, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int currentGrade = (int) (event.getX() / mRatingFocusBitmap.getWidth() + 1);//计算分数
                if (currentGrade < 0) {//限制范围
                    currentGrade = 0;
                }
                if (currentGrade > mRatingNum) {
                    currentGrade = mRatingNum;
                }
                if (mCurrentRatingGrade == currentGrade) {//分数相同不需要绘制
                    return true;
                }
                mCurrentRatingGrade = currentGrade;//记录当前评分
                invalidate();
                //星星一样的时候就不必调用重绘了
                break;
        }
        return true;//如果返回false 第一次进入dwon  down之后的事件就进不来了，return true代表消费这个事件
    }
}
