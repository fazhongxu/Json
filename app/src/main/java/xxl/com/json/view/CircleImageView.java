package xxl.com.json.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/12/12.
 */

public class CircleImageView extends View {
    private Drawable mDrawable;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.根据drawable 获取bitmap对象
        Bitmap bitmap = drawable2Bitmap(mDrawable);

        //2 把bitmap对象转换成圆形图片
        Bitmap circleBitmap = getCircleBitmap(bitmap);

        //3.把圆形bitmap绘制出来
        canvas.drawBitmap(circleBitmap,0,0,null);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建圆形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        canvas.drawCircle(getMeasuredWidth() / 2,getMeasuredHeight() / 2,getMeasuredWidth() / 2,paint);
        //取交集 留出圆形的绘制图片的空位
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //绘制图片在留出的空位上
        canvas.drawBitmap(bitmap,0,0,paint);
        return circleBitmap;
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();
    }
}
