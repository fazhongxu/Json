package xxl.com.json.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import xxl.com.json.R;

/**
 * Created by xxl on 2017/10/25.
 */

public class CustomTimerView extends FrameLayout {

    private TextView mTvHour;
    private TextView mTvMinute;
    private TextView mTvSecond;

    public CustomTimerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomTimerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTimerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_countdown, null);
        mTvHour = (TextView) view.findViewById(R.id.tv_hour);
        mTvMinute = (TextView) view.findViewById(R.id.tv_minute);
        mTvSecond = (TextView) view.findViewById(R.id.tv_second);
        addView(view);
    }

    private void updateTime(String second, String minute, String hour) {
        mTvHour.setText(hour);
        mTvMinute.setText(minute);
        mTvSecond.setText(second);
    }

}
