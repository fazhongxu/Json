package xxl.com.json.navigationbar;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xxl on 2017/12/20.
 */

public interface INavigation {
    /**
     * 创建布局
     */
    void createLayout();

    /**
     * 把创建的布局添加到父布局中
     * @param layoutView
     * @param parent
     */
    void attatchParent(View layoutView, ViewGroup parent);

    /**
     * 绑定参数
     */
    void bindParams();
}
