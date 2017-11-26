package xxl.com.json.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import xxl.com.json.R;
import xxl.com.json.adapter.TabPagerAdapter;

public class TablayoutActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private TabPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);

        mTablayout.post(new Runnable() {
            @Override
            public void run() {
                setTabsWidth(mTablayout,150,150);
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //给ViewPager设置Adapter
        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        //设置Tablayout关联ViewPager
        mTablayout.setupWithViewPager(mViewPager);
    }

    /**
     * set tabsWidth
     */
    private void setTabsWidth(TabLayout tablayout, int left, int right) {
        Class<?> clazz = tablayout.getClass();
        LinearLayout linearLayout = null;
        try {
            Field field = clazz.getDeclaredField("mTabStrip");
            field.setAccessible(true);
            linearLayout = (LinearLayout) field.get(tablayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = linearLayout.getChildAt(i);
            child.setPadding(0,0,0,0);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
            layoutParams.leftMargin = left;
            layoutParams.rightMargin = right;

            child.setLayoutParams(layoutParams);

            child.invalidate();
        }

    }
}
