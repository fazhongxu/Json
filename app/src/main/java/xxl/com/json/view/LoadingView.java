package xxl.com.json.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/12/3.
 */

public class LoadingView extends LinearLayout {
    private ShapeView mShapeView;
    private View mShadowView;
    private int ANIMATOR_DURATION = 500;
    private float mTranslationY;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
        //开始下落动画
        startFallAnimator();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        //View view = LayoutInflater.from(getContext()).inflate(R.layout.ui_loading_view,null);
        //addView(view);
        inflate(getContext(), R.layout.ui_loading_view, this);
        mShapeView = (ShapeView) findViewById(R.id.shape_view);
        mShadowView = findViewById(R.id.shadow_view);
        mTranslationY = dip2px(80);
    }

    /**
     * 开始下落动画
     */
    private void startFallAnimator() {
        //属性动画 下落  越来越快（差值器）
        //形状 下落 的同时 阴影由大变小
        //mShadowView.setTranslationY();
        //mShapeView.setAlpha();
        //mShapeView.setScaleY();
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView,"translationY",0,mTranslationY);
        //translationAnimator.setDuration();
        //translationAnimator.start();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mShadowView,"scaleX",1,0.3F);

        //动画集
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        //设置动画差值器，下落的时候是越来越快，上抛是越来越慢
        animatorSet.setInterpolator(new AccelerateInterpolator());
        //一起执行
        animatorSet.playTogether(translationAnimator,scaleX);

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                //下落动画结束之后接着上抛动画开始执行
                startUpAnimator();
            }
        });

        animatorSet.start();
    }

    /**
     * 开始上抛动画
     */
    private void startUpAnimator() {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY", mTranslationY, 0);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3F,1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATOR_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(translationAnimator,scaleAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startFallAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                //上抛的时候改变形状 并且形状旋转
                //改变图形形状
                mShapeView.changeShape();

                startRotateAnimation();
            }
        });
        animatorSet.start();
    }

    /**
     * 开始旋转
     */
    private void startRotateAnimation() {
        //获取当前形状 旋转  圆形不用旋转，正方形旋转180 三角形旋转60度
        ObjectAnimator scaleAnimator = null;
        switch (mShapeView.getCurrentShape()) {
            case CIRCLE:
            case SQUARE:
                scaleAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,180);
                break;
            case TRIANGLE:
                scaleAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,120);
                break;
        }
        scaleAnimator.setDuration(ANIMATOR_DURATION);
        scaleAnimator.start();
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }
}
