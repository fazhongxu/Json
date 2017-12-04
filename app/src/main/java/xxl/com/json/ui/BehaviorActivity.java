package xxl.com.json.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import xxl.com.json.R;
import xxl.com.json.adapter.TestAdapter;
import xxl.com.json.utils.StatusBarUtils;

public class BehaviorActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private TestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        StatusBarUtils.setStatusBarTransparent(this);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            mList.add("item---" + i);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
