package xxl.com.json.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xxl on 2017/11/27.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void startActivty(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
