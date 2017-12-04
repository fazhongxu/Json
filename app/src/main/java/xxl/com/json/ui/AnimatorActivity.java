package xxl.com.json.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import xxl.com.json.R;
import xxl.com.json.view.LoadingView;

public class AnimatorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        final LoadingView loadingView = (LoadingView) findViewById(R.id.loading_view);
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.setVisibility(View.GONE);
            }
        });
    }
}
