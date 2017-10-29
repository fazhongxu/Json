package xxl.com.json.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import xxl.com.json.R;
import xxl.com.json.common.CustomCountDownTimer;

/**
 * Created by xxl on 2017/10/25.
 */

public class CustomTimerView extends FrameLayout {

    private TextView mTvHour;
    private TextView mTvMinute;
    private TextView mTvSecond;
    private CustomCountDownTimer mCountDownTimer;

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

    /**
     * 设置倒计时
     *
     * @param totalTime
     * @param setupTime
     */
    public void setCountTimer(final int totalTime, int setupTime) {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CustomCountDownTimer(totalTime, setupTime, new CustomCountDownTimer.Lisenter() {
            @Override
            public void onTimerTick(long millisUntilFinished) {
//                long time = millisUntilFinished / 1000;
//                String second = String.valueOf(time % 60);
//                String minute = String.valueOf(time / 60 % 60);
//                String hour = String.valueOf(time / 60 * 60 * 24 % 24);
//                updateTime(second, minute, hour);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = simpleDateFormat.format(millisUntilFinished - TimeZone.getDefault().getRawOffset());
                String[] split = time.split(":");
                String hour = "";
                String minute = "";
                String second = "";
                if (split.length > 0) {
                    hour = split[0];
                    minute = split[1];
                    second = split[2];
                }
                updateTime(second,minute,hour);
            }

            @Override
            public void onTimerFinish() {
                updateTime("","","");
            }
        });
        mCountDownTimer.start();
    }

    private void updateTime(String second, String minute, String hour) {
        mTvHour.setText(hour);
        mTvMinute.setText(minute);
        mTvSecond.setText(second);
    }

}
