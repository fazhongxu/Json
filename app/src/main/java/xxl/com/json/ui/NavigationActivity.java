package xxl.com.json.ui;

import android.os.Bundle;

import xxl.com.json.R;
import xxl.com.json.navigationbar.NavigationBar;

public class NavigationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        NavigationBar navigationBar = new NavigationBar
                .Builder(this, null, R.layout.layout_navigation)
                .create();

    }
}
