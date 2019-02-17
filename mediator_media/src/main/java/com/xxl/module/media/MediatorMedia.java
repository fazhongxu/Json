package com.xxl.module.media;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 2019/2/15
 * <p>
 * Description :
 */
public class MediatorMedia {
    public static void startMediaActivity() {
        ARouter.getInstance().build(IConstantMedia.MEDIA_MEDIA_ACTIVITY).navigation();
    }
}
