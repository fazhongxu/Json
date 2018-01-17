package xxl.com.json.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xxl.com.json.R;
import xxl.com.json.bean.MessageEvent;
import xxl.com.json.navigationbar.DefaultNavigationBar;

/**
 * Created by xxl on 2017/11/26.
 */

public class MovieFragment extends Fragment {

    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);

        DefaultNavigationBar defaultNavigationBar =
                new DefaultNavigationBar
                        .Builder(getContext(),R.layout.layout_navigation,llRoot)
                        .create();

        mTextView = (TextView) view.findViewById(R.id.tv_movie);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.msg = "json";
                EventBus.getDefault().post(messageEvent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        // do something...
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
