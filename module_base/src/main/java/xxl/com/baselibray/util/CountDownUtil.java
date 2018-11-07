package xxl.com.baselibray.util;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xxl on 2018/11/6.
 *
 * Description :
 */

public class CountDownUtil {
    private long mDeadTime;
    private  Observable<Long> mInterval;
    private Disposable mSubscribe;
    private CountDownCallBack mCountDownCallBack;

    private CountDownUtil() {
        mInterval = createInterval();
    }

    /**
     * 开始倒计时
     * @param deadTime
     */
    public void start(long deadTime,CountDownCallBack countDownCallBack) {
        mDeadTime = deadTime;
        long time = mDeadTime - (System.currentTimeMillis() / 1000);  // System.currentTimeMillis() / 1000 毫秒时间戳转换为 秒 时间戳
        if (time <= 0) {
            return;
        }
        if (mInterval == null) {
            mInterval = createInterval();
        }
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
        mCountDownCallBack = countDownCallBack;
        mSubscribe = mInterval
                .take(time + 1)
                .map(aLong -> time - aLong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    long day,hour,minute,second;
                    day = aLong / (24 * 60 * 60);
                    hour = (aLong / (60 *60) ) % 60;   // %60 小时不能大于等于 60
                    minute = (aLong / 60 ) % 60;
                    second = aLong % 60;
                    //Log.e("aaa", "倒计时: "+day+"天"+hour+"小时"+minute+"分钟"+second+"秒");
                    if (countDownCallBack != null) {
                        countDownCallBack.onCountDown(day,hour,minute,second);
                    }
                });
    }

    /**
     * onResume
     */
    public void onResume() {
        start(mDeadTime,mCountDownCallBack);
    }

    /**
     * 停止倒计时
     */
    public void stop() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }

    private static Observable<Long> createInterval() {
        return Observable
                .interval(1000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class Holder {
        private static final CountDownUtil INSTANCE = new CountDownUtil();
    }

    public static CountDownUtil getInstance() {
        return Holder.INSTANCE;
    }

    public interface CountDownCallBack {
        void onCountDown(long day,long hour,long minute,long second);
    }
}
