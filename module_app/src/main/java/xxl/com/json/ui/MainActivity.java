package xxl.com.json.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.json.im.MediatorIM;
import com.xxl.json.im.RongCloudHelpter;
import com.xxl.mediatorweb.MediatorWeb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import cn.sharesdk.onekeyshare.dialog.ShareDialog;
import io.rong.imkit.RongIM;
import xxl.com.baselibray.util.CountDownUtil;
import xxl.com.json.R;
import xxl.com.json.permission.PermissionFailure;
import xxl.com.json.permission.PermissionHelper;
import xxl.com.json.permission.PermissionSuccess;
import xxl.com.json.ui.base.BaseActivity;
import xxl.com.json.view.MultiImageView;
import xxl.com.json.view.SlideBar;
import xxl.com.json.view.TouchView;
@Route( path = "/main/MainActivity")
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnTest;
    private String TAG = "MainActivity";

    private String[] mImages = new String[]{"https://avatars0.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4",
    "https://avatars0.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4",
            "https://avatars0.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4"};

    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 100;
    private TextView mCountDownTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        viewTouch();
        MultiImageView multiImageView = findViewById(R.id.main_activity_miv);
        multiImageView.setImageUrls(mImages);
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mCountDownTv = findViewById(R.id.tv_count_down);
        mBtnTest.setOnClickListener(this);

        SlideBar slideBar = (SlideBar) findViewById(R.id.slide_bar);
        slideBar.setOnTouchLisenter(new SlideBar.OnTouchLisenter() {
            @Override
            public void onTouch(String letter) {
                Toast.makeText(MainActivity.this, "" + letter, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();//迭代器方式删除元素 避免CurrentModifyException
        while (iterator.hasNext()) {
            String temp = iterator.next();
            if ("2".equals(temp)) {
                iterator.remove();
            }
        }

        CountDownUtil.getInstance().start(1571328000,
                new CountDownUtil.CountDownCallBack() {
            @Override
            public void onCountDown(long day, long hour, long minute, long second) {
                mCountDownTv.setText(String.format(Locale.CANADA,"倒计时 %d天:%d小时:%d分:%d秒",day,hour,minute,second));
            }
        });

        RongCloudHelpter.getInstance().connect(token1);

    }

        String token1 = "zwXC8abDqm8yjdk/ZcWlD1h4ynYS1zpYWC51dq9WT6I1G2CwlPe/zu4nIitG31j3iyoLrwflWzD3l7CTMHKU7g==";//json
    String token2 = "Sux96aYwGKaUzp8PuHgcksIMPL4IogNJLQA3Z89wDTsrqA+0uWAM0XnxmwijZcauY9RAmGkL2mwtPWNt7nKKRw==";//123

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                int randomNum = new Random().nextInt(16);
                RongIM.getInstance().startPrivateChat(this, "123", "贝吉塔");  // token1 和 123 （token2) 可以聊天     token 2 和 json(token1) 才可以聊天
                switch (randomNum) {
                    case 0:
                        startActivity(MapActivity.class);
                        break;
                    case 1:
                        startActivity(BehaviorActivity.class);
                        break;
                    case 2:
                        startActivity(AnimatorActivity.class);
                        break;
                    case 3:
                        startActivity(NavigationActivity.class);
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, MvpTestActivity.class));
                        break;
                    case 5:
                        startActivity(TestActivity.class);
                        break;
                    case 6:
                        startActivity(TablayoutActivity.class);
                        break;
                    case 7:
                        startActivity(new Intent(this, CommonToolbarActivity.class));
                        break;
                    case 8:
                        startActivity(DialogActivity.class);
                        break;
                    case 9:
                        //startActivity(BannerActivity.class);
                        ShareDialog dialog = new  ShareDialog.Builder(this)
                        .setTitle("我是标题")
                        .setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526540098112&di=65340416416f4c2a67b15268f870251e&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2Fd6a2565f8d002a8d6d505f095409e8b7399f3d0e.jpg")
                        .setTitleUrl("https://github.com/")
                        .setText("我是分享文本")
                        .show();
                        break;
                    case 10:
                        startActivity(RxJavaActivity.class);
                        break;
                    case 11:
                        startActivity(TestImageSelectActivity.class);
                        break;
                    case 12:
                        startActivity(MarqueeTestActivity.class);
                        break;
                    case 13:
                        startActivity(BannerActivity.class);
                        break;
                    case 14:
                        MediatorWeb.startWeb("https://github.com/fazhongxu");
                        break;
                    case 15:
                       // RongCloudHelpter.getInstance().connect();
                        break;
                    default:
                        startActivity(BannerActivity.class);
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void call() {
        PermissionHelper.with(this)
                .requestCode(CALL_PHONE_PERMISSION_REQUEST_CODE)
                .addPermissions(new String[]{Manifest.permission.CALL_PHONE})
                .request();
    }

    @PermissionSuccess(requstCode = CALL_PHONE_PERMISSION_REQUEST_CODE)
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+0000000"));
        startActivity(intent);
    }

    @PermissionFailure(requstCode = CALL_PHONE_PERMISSION_REQUEST_CODE)
    private void callPhoneFailure() {

    }

    private int keyPressedCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        CountDownUtil.getInstance().onResume();
    }

    @Override
    public void onBackPressed() {
        switch (keyPressedCount++) {
            case 0:
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        keyPressedCount = 0;
                    }
                }, 1000);
                break;
            case 1:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        CountDownUtil.getInstance().stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionResult(this, requestCode, permissions);
    }

    private void viewTouch() {
        TouchView touchView = (TouchView) findViewById(R.id.touchView);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("UserInfoView", "TouchListener: -->" + event.getAction());
                return false;
            }
        });
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("UserInfoView", "onClick:");
            }
        });
    }


}
