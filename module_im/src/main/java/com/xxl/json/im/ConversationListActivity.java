package com.xxl.json.im;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import io.rong.imkit.RongIM;
import xxl.com.baselibray.ui.BaseActivity;

@Route(path = IImConstants.IM_CONVERSATION)
public class ConversationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        //启动会话界面
        //if (RongIM.getInstance() != null)
            //RongIM.getInstance().startPrivateChat(this, "123", "贝吉塔");
    }
}
