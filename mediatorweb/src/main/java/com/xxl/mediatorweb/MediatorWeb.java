package com.xxl.mediatorweb;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 2018/10/23.
 *
 * Description : 中间件件
 */

public class MediatorWeb {
    public static void startWeb(String url) {
        ARouter.getInstance().build(IConstantWeb.WEB_SIMPLE_WEB)
                .withString(IConstantWeb.URL,url)
                .navigation();
    }
}
