package com.xxl.modelweb;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.mediatorweb.IConstantWeb;
import com.xxl.mediatorweb.IWebProvider;

/**
 * Created by xxl on 2018/10/28.
 *
 * Description :
 */

@Route(path = IConstantWeb.WEB_PROVIDER)
public class WebProviderImpl implements IWebProvider {
    @Override
    public void init(Context context) {

    }

    @Override
    public String getUserName() {
        return String.valueOf("xxl");
    }

    @Override
    public String getWebUrl() {
        return SimpleWebViewActivity.getUrl();
    }


}
