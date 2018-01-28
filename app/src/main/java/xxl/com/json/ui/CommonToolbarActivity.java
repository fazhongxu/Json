package xxl.com.json.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import xxl.com.json.R;
import xxl.com.json.commontoolbar.CommonToolbar;
import xxl.com.json.ui.base.BaseActivity;

public class CommonToolbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toolbar);


        //ViewGroup root = (ViewGroup) findViewById(R.id.ll_root);

        new CommonToolbar.Builder(this)
                .setBackgroundColorResorce(R.color.colorAccent)
                .setLeftIcon(1,R.drawable.ic_back,40,40,null)
                .setLeftText(2,"Left",30,null)
                .setRightText(3,"Right",null)
                .setRightIcon(4,R.drawable.ic_favorite, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CommonToolbarActivity.this, "favorite", Toast.LENGTH_SHORT).show();
                    }
                })
                .setTitleText("标题",18)
                .setTextColor(Color.WHITE)
                .apply();
    }
}
