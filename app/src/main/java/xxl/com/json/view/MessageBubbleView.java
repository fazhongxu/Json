package xxl.com.json.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/12/5.
 */

public class MessageBubbleView extends View {
    public MessageBubbleView(Context context) {
        this(context,null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //绘制内外圆 内圆固定 半径随着外圆的距离不断变化 外圆可移动 半径固定
    }
}
