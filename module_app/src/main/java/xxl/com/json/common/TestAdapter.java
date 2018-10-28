package xxl.com.json.common;

import android.content.Context;

import java.util.List;

import xxl.com.json.R;
import xxl.com.json.bean.ChatBean;
import xxl.com.json.commonadapter.CommonRecyclerViewAdapter;
import xxl.com.json.commonadapter.MultiTypeSupport;

/**
 * Created by xxl on 2017/10/26.
 */

public class TestAdapter extends CommonRecyclerViewAdapter<ChatBean> {
    public TestAdapter(Context context, List<ChatBean> data) {
        super(context, data, new MultiTypeSupport<ChatBean>() {
            @Override
            public int getLayoutId(ChatBean chatBean) {
                if (chatBean.getIsMe() == 1) {
                    return R.layout.item_chat_me;
                } else {
                    return R.layout.item_chat_friend;
                }
            }
        });
    }

    @Override
    protected void convert(ViewHolder holder, ChatBean data) {
        holder.setText(R.id.tv_chat_content, data.getChatContent());
    }
}
