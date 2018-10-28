package xxl.com.json.common;

import android.os.CountDownTimer;

/**
 * Created by xxl on 2017/10/25.
 */

public class CustomCountDownTimer extends CountDownTimer {
    private Lisenter lisenter;

    /**
     * @param millisInFuture    总长
     * @param countDownInterval 间隔
     * @param lisenter          监听
     */
    public CustomCountDownTimer(long millisInFuture, long countDownInterval, Lisenter lisenter) {
        super(millisInFuture, countDownInterval);
        this.lisenter = lisenter;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (lisenter != null)
            lisenter.onTimerTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        if (lisenter != null)
            lisenter.onTimerFinish();
    }

    public interface Lisenter {
        void onTimerTick(long millisUntilFinished);

        void onTimerFinish();
    }
}
