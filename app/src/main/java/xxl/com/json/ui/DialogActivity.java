package xxl.com.json.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import xxl.com.baselibray.dialog.AlertDialog;
import xxl.com.json.R;
import xxl.com.json.ui.base.BaseActivity;

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    private Button mFromBottom;
    private Button mFromCenter;
    private Button mFillWidth;
    private Button mWithAnimator;
    private Button mCancelable;
    private Button mVisible;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
    }

    private void initView() {
        mFromBottom = (Button) findViewById(R.id.btn_from_bottom);
        mFromCenter = (Button) findViewById(R.id.btn_from_center);
        mFillWidth = (Button) findViewById(R.id.btn_fill_width);
        mWithAnimator = (Button) findViewById(R.id.btn_with_animator);
        mCancelable = (Button) findViewById(R.id.btn_cancelable);
        mVisible = (Button) findViewById(R.id.btn_visible);

        mFromBottom.setOnClickListener(this);
        mFromCenter.setOnClickListener(this);
        mFillWidth.setOnClickListener(this);
        mWithAnimator.setOnClickListener(this);
        mCancelable.setOnClickListener(this);
        mVisible.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_from_bottom:
                mAlertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .setFromBottom(false)
                        .setOnClickLisenter(R.id.weixin_image, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DialogActivity.this, "微信", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

                mAlertDialog.setOnClickLisenter(R.id.submit_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String text = mAlertDialog.getText(R.id.comment_text);
                        if (TextUtils.isEmpty(text)) {
                            Toast.makeText(DialogActivity.this, "输入内容不能为空" + text, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(DialogActivity.this, "" + text, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_from_center:
                mAlertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .show();
                break;
            case R.id.btn_fill_width:
                mAlertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .setFromBottom(false)
                        .setFullWidth()
                        .show();
                break;
            case R.id.btn_with_animator:
                mAlertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .setFromBottom(true)
                        .setFullWidth()
                        .show();
                break;
            case R.id.btn_cancelable:
                mAlertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .setFromBottom(true)
                        .setFullWidth()
                        .setCancelable(false)
                        .setDefaultAnimation()
                        .show();
                mAlertDialog.setOnClickLisenter(R.id.submit_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });
                break;
            case R.id.btn_visible:
                mAlertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .setFromBottom(true)
                        .setFullWidth()
                        .setVisible(R.id.weibo_image,false)
                        .show();
                break;
            default:
                break;
        }
    }
}
