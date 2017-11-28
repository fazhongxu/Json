package xxl.com.json.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;
import xxl.com.baselibray.http.HttpUtil;
import xxl.com.json.R;
import xxl.com.json.bannerview.BannerAdapter;
import xxl.com.json.bannerview.BannerView;
import xxl.com.json.bean.JokeBean;
import xxl.com.json.bean.Person;
import xxl.com.json.permission.PermissionFailure;
import xxl.com.json.permission.PermissionHelper;
import xxl.com.json.permission.PermissionSuccess;
import xxl.com.json.view.SlideBar;
import xxl.com.json.view.TouchView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnTest;
    private BannerView mBannerView;
    private String TAG = "MainActivity";

    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       TouchView touchView = (TouchView) findViewById(R.id.touchView);
       touchView.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Log.e("View", "TouchListener: -->"+event.getAction());
               return false;
           }
       });
       touchView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.e("View", "onClick:");
           }
       });

        initData();
        initView();
    }

    private void initData() {
        String url = "http://is.snssdk.com/2/essay/discovery/v3/?&device_platform=android&device_type=Redmi+Note+3&iid=6152551759&manifest_version_code=570&longitude=113.000366&latitude=28.171377&update_version_code=5701&aid=7&channel=360";
        HttpUtil.with(this)
                .url(url)
                .get()
                .execute(new HttpCallBackEngine() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e(TAG, "onSuccess: " + result);
                        Gson gson = new Gson();
                        JokeBean jokeBean = gson.fromJson(result, JokeBean.class);
                        final JokeBean.DataBean.RotateBannerBean rotate_banner = jokeBean.getData().getRotate_banner();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setBanner(rotate_banner);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onPreExcute(Context context, Map<String, Object> params) {

                    }
                });
    }

    private void setBanner(final JokeBean.DataBean.RotateBannerBean bannerBean) {
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView imageView = new ImageView(MainActivity.this);
                Glide.with(MainActivity.this)
                        .load(bannerBean.getBanners().get(position).getBanner_url().getUrl_list().get(0).getUrl())
                        .into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return bannerBean.getBanners().size();
            }

            @Override
            public String getBannerDes(int position) {
                return bannerBean.getBanners().get(position).getBanner_url().getTitle();
            }
        });
        mBannerView.startScroll();
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);

        mBannerView = (BannerView) findViewById(R.id.banner_view);

        SlideBar slideBar = (SlideBar) findViewById(R.id.slide_bar);
        slideBar.setOnTouchLisenter(new SlideBar.OnTouchLisenter() {
            @Override
            public void onTouch(String letter) {
                Toast.makeText(MainActivity.this, "" + letter, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                try {
                    Person person = new Person();
//                    Person person = Person.class.newInstance();
                    Class<?> clazz = Class.forName("xxl.com.json.bean.Person");
                    Field name = clazz.getDeclaredField("name");
                    name.setAccessible(true);
                    Log.e(TAG, "onClick: "+name.get(person));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                startActivty(TestActivity.class);

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
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+0000000"));
        startActivity(intent);
    }

    @PermissionFailure(requstCode = CALL_PHONE_PERMISSION_REQUEST_CODE)
    private void callPhoneFailure() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionResult(this, requestCode, permissions);
    }
}
