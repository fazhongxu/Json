package xxl.com.json.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import xxl.com.json.R;
import xxl.com.json.adapter.GirlFragmentPagerAdapter;
import xxl.com.json.mvp.demo4.base.BaseMvpActivity;
import xxl.com.json.mvp.demo4.base.BasePresenter;
import xxl.com.json.ui.fragment.GirlFragment;

public class GirlActivity extends BaseMvpActivity {

    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private GirlFragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> mFragments;
    private String[] mTitles = new String[]{"自拍","最近","最热","推荐","性感","日本","台湾","清纯"};

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        initToolBar();

        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(GirlFragment.newInstance(i,mTitles));
        }
        mFragmentPagerAdapter = new GirlFragmentPagerAdapter(getSupportFragmentManager(), mFragments,mTitles);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ShowGirl");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置系统返回按钮显示
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_girl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//toolbar菜单创建
        getMenuInflater().inflate(R.menu.menu_girl,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://toolbar上系统返回按钮点击事件
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //反射让文字和图标同时显示
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
