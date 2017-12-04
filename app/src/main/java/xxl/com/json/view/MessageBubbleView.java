package xxl.com.json.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xxl on 2017/12/5.
 */

public class MessageBubbleView extends View {
    private Paint mPaint;
    //固定圆的点 和 移动圆的点
    private PointF mFixationCirclePoint, mMoveCirclePoint;
    private int mFixationCircleRadius;//固定圆的半径
    private int mFixationCircleMaxRadius = 7;//最大半径
    private int mFixationCircleMinRadius = 3;//最小半径
    private int mMoveCircleRadius = 10;

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFixationCircleMaxRadius = dip2px(mFixationCircleMaxRadius);
        mFixationCircleMinRadius = dip2px(mFixationCircleMinRadius);
        mMoveCircleRadius = dip2px(mMoveCircleRadius);
        //绘制内外圆 内圆固定 半径随着外圆的距离不断变化 外圆可移动 半径固定

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制外圆 绘制内圆
        if (mFixationCirclePoint == null || mMoveCirclePoint == null) return;
        canvas.drawCircle(mMoveCirclePoint.x, mMoveCirclePoint.y, mMoveCircleRadius, mPaint);

        mFixationCircleRadius = getFixationRadius(mMoveCirclePoint.x, mMoveCirclePoint.y);
        canvas.drawCircle(mFixationCirclePoint.x, mFixationCirclePoint.y, mFixationCircleRadius, mPaint);
    }

    private int getFixationRadius(float x, float y) {
        //根据移动圆的移动的距离来设置固定圆的半径大小
        double distance = Math.sqrt((x - mFixationCirclePoint.x) * (x - mFixationCirclePoint.x) + (y - mFixationCirclePoint.y) * (y - mFixationCirclePoint.y));
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
                initPoint(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                updateMoveCirclePoint(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 更新移动圆的坐标
     */
    private void updateMoveCirclePoint(float moveX, float moveY) {
        mMoveCirclePoint.x = moveX;
        mMoveCirclePoint.y = moveY;
    }

    /**
     * 初始化按下点的点坐标
     */
    private void initPoint(float downX, float downY) {
        mFixationCirclePoint = new PointF(downX, downY);
        mMoveCirclePoint = new PointF(downX, downY);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

}
