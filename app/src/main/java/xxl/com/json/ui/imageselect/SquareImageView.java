package xxl.com.json.ui.imageselect;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by xxl on 2018/3/25.
 * Email :
 * Description : 正方形ImageView
 */

public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        this(context,null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width;
        setMeasuredDimension(width,height);
    }
}
