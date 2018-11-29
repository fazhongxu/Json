package com.xxl.json.im;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import xxl.com.baselibray.ui.BaseActivity;

@Route(path = IImConstants.IM_CONVERSATION)
public class ConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

    }
}
