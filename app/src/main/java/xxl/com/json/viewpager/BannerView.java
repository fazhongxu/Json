package xxl.com.json.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/10/28.
 * 自定义ViewPager 显示的View 添加BannerViewPager，文字描述和小点
 */

public class BannerView extends RelativeLayout {
    private Context mContext;
    private BannerViewPager mBannerViewPager;
    private TextView mDes;
    private LinearLayout mLLDotIndicator;
    private BannerAdapter mBannerAdapter;
    private Drawable mIndecatorNormal;//点未选中drawble
    private Drawable mIndecatorSelect;//点选中drawable
    private int mCurrentPosition = 0;//当前选中的页面位置,默认位置是第0个位置

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        inflate(context, R.layout.ui_banner_layout, this);
        initView();

        mIndecatorNormal = new ColorDrawable(Color.WHITE);
        mIndecatorSelect = new ColorDrawable(Color.RED);
    }


    private void initView() {
        mBannerViewPager = (BannerViewPager) findViewById(R.id.banner_vp);
        mDes = (TextView) findViewById(R.id.tv_des);
        mLLDotIndicator = (LinearLayout) findViewById(R.id.ll_dot_container);
    }

    /**
     * 设置Adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdapter = adapter;
        mBannerViewPager.setAdapter(adapter);

        //设置广告位小点
        initDotIndicator();

        //监听ViewPager滑动
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pagerSelected(position);
            }
        });

        //初始化第一个位置的广告位文字描述
        String bannerDes = mBannerAdapter.getBannerDes(0);
        mDes.setText(bannerDes);
    }

    /**
     * 根据切换页面改变小点状态和文字描述
     */
    private void pagerSelected(int position) {
        //恢复上一个点的显示，显示为默认的状态
        IndicatorView oldDotView = (IndicatorView) mLLDotIndicator.getChildAt(mCurrentPosition);
        oldDotView.setDrawble(mIndecatorNormal);

        //设置当前点的状态为选中状态
        mCurrentPosition = position % mBannerAdapter.getCount();
        IndicatorView currentDotView = (IndicatorView) mLLDotIndicator.getChildAt(mCurrentPosition);
        currentDotView.setDrawble(mIndecatorSelect);

        //设置广告位文字描述
        String bannerDes = mBannerAdapter.getBannerDes(mCurrentPosition);
        mDes.setText(bannerDes);
    }

    /**
     * 初始化小点
     */
    private void initDotIndicator() {
        int count = mBannerAdapter.getCount();//获取广告位数量
        for (int i = 0; i < count; i++) {
            //根据 条目图片张数动态添加小点
            IndicatorView indicatorView = new IndicatorView(mContext);
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(8), dip2px(8));
            params.leftMargin = dip2px(2);
            params.rightMargin = dip2px(2);
            //设置Drawable 背景
            if (i == 0) {
                indicatorView.setDrawble(mIndecatorSelect);
            } else {
                indicatorView.setDrawble(mIndecatorNormal);
            }
            indicatorView.setLayoutParams(params);
            mLLDotIndicator.addView(indicatorView);
        }
    }

    /**
     * dip 转px
     */
    private int dip2px(int dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, getResources().getDisplayMetrics());
    }

    /**
     * 设置ViewPager自动滚动
     */
    public void startScroll() {
        mBannerViewPager.startScroll();
    }
}
