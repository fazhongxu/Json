package xxl.com.json.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/11/3.
 */

public class ColorFlowTextView extends AppCompatTextView {

    private int mOrginColor;
    private int mChangeColor;
    private Paint mOriginPaint;
    private Paint mChangePaint;
    private float mCurrentProgress;
    private Direction mDirection;

    /**
     * 绘制方向
     */
    public enum Direction{
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public ColorFlowTextView(Context context) {
        this(context, null);
    }

    public ColorFlowTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFlowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    /**
     * 初始化画笔和自定义属性
     */
    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorFlowTextView);
        mOrginColor = typedArray.getColor(R.styleable.ColorFlowTextView_orginColor, mOrginColor);
        mChangeColor = typedArray.getColor(R.styleable.ColorFlowTextView_changeColor, mChangeColor);
        typedArray.recycle();

        mOriginPaint = getPaintByColor(mOrginColor);
        mChangePaint = getPaintByColor(this.mChangeColor);
    }

    /**
     * 根据颜色获取不同颜色的画笔
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //实现原理  绘制文字，裁剪画板，让画板只显示哪一部分
        //绘制了部分重叠的文字，只是裁剪画板的时候让其显示的区域不一样，形成一半是另一种颜色的效果
        if (mDirection == Direction.LEFT_TO_RIGHT) {//从左到右边  蓝色被红色覆盖
            drawColorText(canvas,mChangePaint,mOriginPaint);
        }else {
            drawColorText(canvas,mOriginPaint,mChangePaint);
        }
    }

    /**
     * 绘制变色的文字
     */
    private void drawColorText(Canvas canvas,Paint orginPaint,Paint changePaint) {
        //绘制不变色的文字
        canvas.save();//保存画板
        int progress = (int) (mCurrentProgress * getWidth());
        Rect rect = new Rect(0, 0, progress, getHeight());
        canvas.clipRect(rect);//裁剪区域，绘制左边区域
        Paint.FontMetrics fontMetrics = mOriginPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(getText(), 0, getText().length(), 0, baseLine, orginPaint);
        canvas.restore();//重置画板

        canvas.save();
        //绘制右边
        rect = new Rect(progress, 0, getWidth(), getHeight());
        canvas.clipRect(rect);
        canvas.drawText(getText(), 0, getText().length(), 0, baseLine, changePaint);
        canvas.restore();
    }

    /**
     *设置颜色改变进度
     */
    public void setProgress(float progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }

    /**
     * 设置绘制方向
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }
}
