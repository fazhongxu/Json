package xxl.com.json.manager;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by xxl on 2017/11/29.
 * Fragment管理类
 */

public class FragmentManagerHelper {

    private FragmentManager mFragmentManager;
    private int mContainerId;//容器Id

    public FragmentManagerHelper(@NonNull FragmentManager fragmentManager, @IdRes int containerId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerId = containerId;
    }


    /**
     * 添加Fragment
     */
    public void add(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(mContainerId, fragment);
        transaction.show(fragment);
        transaction.commit();
    }

    /**
     * 切换Fragment
     */
    @SuppressLint("RestrictedApi")
    public void change(Fragment fragment) {
        //先隐藏所有的fragment，再显示切换到的
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> childFragments = mFragmentManager.getFragments();
        for (Fragment childFragment : childFragments) {
            transaction.hide(childFragment);
        }
        if (!childFragments.contains(fragment)) {//如果不在集合中，添加到容器中，否则直接显示
            transaction.add(mContainerId, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();

    }
}
