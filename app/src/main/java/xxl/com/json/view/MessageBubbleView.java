package xxl.com.json.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    //固定圆的点 和 拖拽圆的点
    private PointF mFixationCirclePoint, mDragCirclePoint;
    private int mFixationCircleRadius;//固定圆的半径
    private int mFixationCircleMaxRadius = 7;//最大半径
    private int mFixationCircleMinRadius = 3;//最小半径
    private int mDragCircleRadius = 10;

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
        mDragCircleRadius = dip2px(mDragCircleRadius);
        //绘制内外圆 内圆固定 半径随着外圆的距离不断变化 外圆可移动 半径固定

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mFixationCirclePoint == null || mDragCirclePoint == null) {
            return;
        }
        //拖拽圆
        canvas.drawCircle(mDragCirclePoint.x, mDragCirclePoint.y, mDragCircleRadius, mPaint);
        double distance = getDistance(mFixationCirclePoint, mDragCirclePoint);
        mFixationCircleRadius = (int) (mFixationCircleMaxRadius - distance / 14);//求固定圆的半径 两圆距离越远，固定圆的半径就越小
        if (mFixationCircleRadius < mFixationCircleMinRadius) {//如果半径值小于最小值 就不要绘制固定圆了
            return;
        }
        //固定圆
        canvas.drawCircle(mFixationCirclePoint.x, mFixationCirclePoint.y, mFixationCircleRadius, mPaint);

        //绘制贝塞尔曲线
        // 获取贝塞尔路径
        Path bezierPath = getBezierPath();

        canvas.drawPath(bezierPath, mPaint);

    }

    /**
     * 获取贝塞尔路径
     */
    private Path getBezierPath() {
        //求出四个点的位置坐标，求出点的x,y值
        //首先求出关于点所在三角形内部的角度
        //固定圆和拖拽圆的 中心连线的斜率
        double dy = mDragCirclePoint.y - mFixationCirclePoint.y;
        double dx = mDragCirclePoint.x - mFixationCirclePoint.x;
        if (dx == 0) {
            dx = 0.001F;
        }
        //斜率
        double tanA = dy / dx;
        //角度
        double atan = Math.atan(tanA);

        //p0(p0x,p0y) p0x = c1x + r * sin(atan)  p0y = c1y - r * cos(atan)
        float p0x = (float) (mFixationCirclePoint.x + mFixationCircleRadius * Math.sin(atan));
        float p0y = (float) (mFixationCirclePoint.y - mFixationCircleRadius * Math.cos(atan));

        //p1
        float p1x = (float) (mDragCirclePoint.x + mDragCircleRadius * Math.sin(atan));
        float p1y = (float) (mDragCirclePoint.y - mDragCircleRadius * Math.cos(atan));

        //p2
        float p2x = (float) (mDragCirclePoint.x - mDragCircleRadius * Math.sin(atan));
        float p2y = (float) (mDragCirclePoint.y + mDragCircleRadius * Math.cos(atan));

        //p3
        float p3x = (float) (mFixationCirclePoint.x - mFixationCircleRadius * Math.sin(atan));
        float p3y = (float) (mFixationCirclePoint.y + mFixationCircleRadius * Math.cos(atan));

        PointF controlPoint = getBezierControlPoint(mFixationCirclePoint, mDragCirclePoint);

        Path path = new Path();
        path.moveTo(p0x, p0y);//moveTo 移动到~
        path.quadTo(controlPoint.x, controlPoint.y, p1x, p1y);//贝塞尔曲线 x1 x2 为控制点  x2,y2为结束点
        path.lineTo(p2x, p2y);
        path.quadTo(controlPoint.x, controlPoint.y, p3x, p3y);
        path.close();//关闭

        return path;
    }

    /**
     * 获取控控制点
     */
    public PointF getBezierControlPoint(PointF fixactionCirclePoint, PointF dragCirclePoint) {
        PointF pointF = new PointF();
        pointF.x = (fixactionCirclePoint.x + dragCirclePoint.x) / 2;
        pointF.y = (fixactionCirclePoint.y + dragCirclePoint.y) / 2;
        return pointF;
    }

    /**
     * 获取两点之间的距离
     */
    private double getDistance(PointF p1,PointF p2){
        return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) +(p1.y - p2.y)*(p1.y - p2.y));
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
                updateDragCirclePoint(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 更新拖拽圆的坐标
     */
    private void updateDragCirclePoint(float moveX, float moveY) {
        mDragCirclePoint.x = moveX;
        mDragCirclePoint.y = moveY;
    }

    /**
     * 初始化按下点的点坐标
     */
    private void initPoint(float downX, float downY) {
        mFixationCirclePoint = new PointF(downX, downY);
        mDragCirclePoint = new PointF(downX, downY);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
