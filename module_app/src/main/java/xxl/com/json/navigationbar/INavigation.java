package xxl.com.json.navigationbar;

import android.view.View;

/**
 * Created by xxl on 2017/12/21.
 */

public interface INavigation {
    /**
     * 创建布局
     */
    void createLayout();

    /**
     * 将创建的 添加到父容器布局中
     */
    void attatchParent(View view);

    /**
     * 绑定参数
     */
    void bindParams();

}
