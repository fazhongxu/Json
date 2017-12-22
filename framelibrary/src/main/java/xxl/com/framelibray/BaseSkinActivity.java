package xxl.com.framelibray;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by xxl on 2017/12/22.
 */

public class BaseSkinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //换肤加添加的中间层，在view创建之前拦截view的创建
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                if ("TextView".equals(name)){//拦截到TextView 把TextView替换成Button
                    Button button = new Button(BaseSkinActivity.this);
                    button.setText("HelloWorld");
                    return button;
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
    }
}
