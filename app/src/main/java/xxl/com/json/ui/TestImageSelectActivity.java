package xxl.com.json.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import xxl.com.json.R;
import xxl.com.json.ui.base.BaseActivity;
import xxl.com.json.ui.imageselect.ImageSelectActivity;

public class TestImageSelectActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList mImageSelectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_select);

        initView();
    }

    private void initView() {
        Button btnTest = (Button) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                Intent intent = new Intent(this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.EXTRA_MAX_COUNT,9);
                intent.putExtra(ImageSelectActivity.EXTRA_SELECT_MODE,ImageSelectActivity.SELECT_MULTI);
                intent.putExtra(ImageSelectActivity.EXTRA_CAMERA,true);
                intent.putStringArrayListExtra(ImageSelectActivity.EXTRA_SELECT_LIST,mImageSelectList);
                startActivityForResult(intent,ImageSelectActivity.REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageSelectActivity.REQUEST_CODE){
            Bundle extras = data.getExtras();
            if (extras != null){
                mImageSelectList = extras.getStringArrayList(ImageSelectActivity.EXTRA_SELECT_LIST);
            }
        }
    }
}
