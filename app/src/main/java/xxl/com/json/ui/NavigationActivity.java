package xxl.com.json.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import xxl.com.json.R;
import xxl.com.json.navigationbar.DefaultNavigationBar;

public class NavigationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this,R.layout.layout_navigation)
                .setLeftText("Back")
                //.setLeftIcon(R.drawable.ic_background)
//                .setLeftHidden(true)
                .setTitle("Title")
                .setRightText("Right")
                .setRightClickLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NavigationActivity.this, "right", Toast.LENGTH_SHORT).show();
                    }
                })
                .setLeftClickLisenter(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .create();
    }
}
