package com.xxl.mediatorweb;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 2018/10/23.
 *
 * Description : 中间件件
 */

public class MediatorWeb {

    private static IWebProvider mProvider;

    public static void startWeb(String url) {
        ARouter.getInstance().build(IConstantWeb.WEB_SIMPLE_WEB)
                .withString(IConstantWeb.URL,url)
                .navigation();
    }

    public static IWebProvider getWebProvider() {
        if (mProvider == null) {
            mProvider = (IWebProvider) ARouter.getInstance()
                    .build(IConstantWeb.WEB_PROVIDER)
                    .navigation();
        }
        return mProvider;
    }

    public static String getUsername() {
       return getWebProvider().getUserName();
    }

    public static String getWebUrl() {
        return getWebProvider().getWebUrl();
    }
}
