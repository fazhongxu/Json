package com.xxl.mediatorweb;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by xxl on 2018/10/23.
 *
 * Description :
 */

public interface IWebProvider extends IProvider {

    String getUserName();

    String getWebUrl();
}
