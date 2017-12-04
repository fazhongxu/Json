package xxl.com.json.ui;

import android.os.Bundle;
import android.view.View;

import xxl.com.json.R;
import xxl.com.json.view.LoadingCircleView;
import xxl.com.json.view.LoadingView;

public class AnimatorActivity extends BaseActivity implements View.OnClickListener {

    private LoadingView mLoadingView;
    private LoadingCircleView mLoadingCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        initView();
    }

    private void initView() {
        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        mLoadingCircleView = (LoadingCircleView) findViewById(R.id.loading_circle_view);
        mLoadingView.setOnClickListener(this);
        mLoadingCircleView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loading_view:
                mLoadingView.setVisibility(View.GONE);
                break;
            case R.id.loading_circle_view:
                mLoadingCircleView.setVisibility(View.GONE);
                break;
        }
    }
}
