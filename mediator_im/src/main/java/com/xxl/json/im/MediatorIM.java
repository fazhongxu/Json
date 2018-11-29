package com.xxl.json.im;

import com.alibaba.android.arouter.launcher.ARouter;

public class MediatorIM {

    public static void startIM() {
        ARouter.getInstance()
                .build(IImConstants.IM_CONVERSATION)
                .navigation();
    }
}
