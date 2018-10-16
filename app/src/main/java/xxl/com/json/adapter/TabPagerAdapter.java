package xxl.com.json.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import xxl.com.json.ui.fragment.MovieFragment;
import xxl.com.json.ui.fragment.MusicFragment;

/**
 * Created by xxl on 2017/11/26.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] mFragments = new Fragment[]{
        new MovieFragment(),
        new MusicFragment()
    };
    private String[] mTitles = new String[]{
            "电影","音乐"
    };
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
