package xxl.com.json.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/12/2.
 */

public class StatusBarUtils {
    public static void setStatusBarColor(Activity activity, int color) {
        // 5.0 以上，直接设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0，默认是黑色的，需要获取DecorView添加，并且需要设置全屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //DecorView（Framlayout）里面加载系统默认布局 系统布局是一个LinearLayout，
            //里面会加载一个android.R.id.content的RelativeLayout
            //我们自己的布局是添加到这个RelativeLayout里面的
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, getStatuBarHeight(activity));
            params.height = getStatuBarHeight(activity);
            View view = new View(activity);
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            decorView.addView(view);
        }
    }

    public static void setStatusBarTransparent(Activity activity) {
        // 5.0 以上，直接设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          /*  View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);*/
            ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatuBarHeight(activity));
            View view = new View(activity);
            view.setLayoutParams(params);
            content.addView(view);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0，默认是黑色的，需要获取DecorView添加，并且需要设置全屏
        }
    }

    public static int getStatuBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int result = 0;
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            result = resources.getDimensionPixelOffset(identifier);
        }
        return result;
    }
}
