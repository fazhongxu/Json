package xxl.com.json.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by xxl on 2017/12/4.
 */

public class LoadingCircleView extends RelativeLayout {
    private Context mContext;
    private CircleView mLeftCircle, mMiddleCircle, mRightCircle;
    private final int CIRCLE_WIDTH = dip2px(10);
    private final float MOVE_DISTANCE = dip2px(20);
    private final Long ANIMATOR_DURATION = 350L;
    private boolean mViewIsGone = false;//布局是否隐藏

    public LoadingCircleView(Context context) {
        this(context, null);
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        setBackgroundColor(Color.WHITE);

        initLayout();

        //测量完成才能开始执行动画,先获取到控件
        post(new Runnable() {
            @Override
            public void run() {
                //执行动画
                expandAnimator();
            }
        });
    }

    @Override
    public void setVisibility(int visibility) {
        setVisibility(INVISIBLE);
        mLeftCircle.clearAnimation();
        mMiddleCircle.clearAnimation();
        mRightCircle.clearAnimation();
        mViewIsGone = true;
    }

    /**
     * 执行展开动画
     */
    private void expandAnimator() {
        if (mViewIsGone){
            return;
        }
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftCircle, "translationX", 0, -MOVE_DISTANCE);

        final ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightCircle, "translationX", 0, MOVE_DISTANCE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());// 快--> 慢
        animatorSet.playTogether(leftAnimator, rightAnimator);//一起执行
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //执行折叠动画
                foldAnimator();
            }
        });
        animatorSet.start();
    }

    /**
     * 圆的颜色切换
     */
    private void changeColor() {
        int leftColor = mLeftCircle.getCurrentColor();
        int rightColor = mRightCircle.getCurrentColor();
        int middleColor = mMiddleCircle.getCurrentColor();

        mMiddleCircle.changeColor(leftColor);
        mRightCircle.changeColor(middleColor);
        mLeftCircle.changeColor(rightColor);
    }

    /**
     * 折叠动画
     */
    private void foldAnimator() {
        if (mViewIsGone){
            return;
        }
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftCircle, "translationX", -MOVE_DISTANCE, 0);

        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightCircle, "translationX", MOVE_DISTANCE, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        animatorSet.setInterpolator(new AccelerateInterpolator());//越来越快
        animatorSet.playTogether(leftAnimator, rightAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                expandAnimator();
                //展开的时候切换颜色 左边的颜色给中间的 中间的颜色给右边的，右边的颜色给左边的
                changeColor();
            }
        });
        animatorSet.start();
    }

    private void initLayout() {
        mLeftCircle = getCircleView();
        mLeftCircle.changeColor(Color.RED);
        mRightCircle = getCircleView();
        mRightCircle.changeColor(Color.CYAN);
        mMiddleCircle = getCircleView();
        mMiddleCircle.changeColor(Color.GREEN);

        addView(mLeftCircle);
        addView(mRightCircle);
        addView(mMiddleCircle);
    }

    /**
     * 获取小圆
     */
    public CircleView getCircleView() {
        CircleView circleView = new CircleView(mContext);
        LayoutParams layoutParams = new LayoutParams(CIRCLE_WIDTH, CIRCLE_WIDTH);
        layoutParams.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(layoutParams);
        return circleView;
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
