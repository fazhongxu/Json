package xxl.com.json.commonadapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xxl on 2017/10/26.
 * RecyclerView自定义分割线
 */

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDrawable;

    public RecyclerViewDecoration(Context context, int drawableResId) {
        mDrawable = ContextCompat.getDrawable(context, drawableResId);
    }

    //留出绘制分割线的位置
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (0 != parent.getChildAdapterPosition(view))
            outRect.top = mDrawable.getIntrinsicHeight();
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        //计算绘制的位置
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingLeft();
        for (int i = 1; i < childCount; i++) {
            //底部刚好是下一个条目的顶部
            rect.bottom = parent.getChildAt(i).getTop();
            //顶部是底部距离减去绘制drable的高度
            rect.top = rect.bottom - mDrawable.getIntrinsicHeight();

            mDrawable.setBounds(rect);
            //1canvas方式绘制
            mDrawable.draw(canvas);

            //2用canvas方式绘制矩形，需要画笔
            //canvas.drawRect(rect, mPaint);
        }
    }
}
