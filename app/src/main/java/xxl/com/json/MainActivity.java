package xxl.com.json;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;
import xxl.com.baselibray.http.HttpUtil;
import xxl.com.json.bannerview.BannerAdapter;
import xxl.com.json.bannerview.BannerView;
import xxl.com.json.bean.JokeBean;
import xxl.com.json.permission.PermissionFailure;
import xxl.com.json.permission.PermissionHelper;
import xxl.com.json.permission.PermissionSuccess;
import xxl.com.json.permission.PermissionUtils;
import xxl.com.json.view.SlideBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTest;
    private BannerView mBannerView;
    private String TAG = "MainActivity";

    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Toast.makeText(MainActivity.this, ""+letter, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
//                startActivity(new Intent(this,TestActivity.class));
                //6.0以上，检查权限是否申请过了
              /*  if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //如果没有申请，就申请权限，然后再onRequstPermission回调里面处理
                    Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_PERMISSION_REQUEST_CODE);
                }else {
                //如果申请过了，直接拨打电话
                     callPhone();
                }*/

//                PermissionHelper.requestPermission(this,CALL_PHONE_PERMISSION_REQUEST_CODE,new String[]{Manifest.permission.CALL_PHONE});
                PermissionHelper.with(this)
                        .requestCode(CALL_PHONE_PERMISSION_REQUEST_CODE)
                        .addPermissions(new String[]{Manifest.permission.CALL_PHONE})
                        .request();
                break;
            default:
                break;
        }
    }

    @PermissionSuccess(requstCode = CALL_PHONE_PERMISSION_REQUEST_CODE)
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+400123456"));
        startActivity(intent);
    }

    @PermissionFailure(requstCode = CALL_PHONE_PERMISSION_REQUEST_CODE)
    private void callPhoneFailure(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       /* if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults != null && grantResults.length > 0) {
               if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   //用户同意授权了
                   Toast.makeText(this, "用户同意授权了", Toast.LENGTH_SHORT).show();
                   callPhone();
               }else {
                   Toast.makeText(this, "用户取消授权了", Toast.LENGTH_SHORT).show();
               }
            }
        }*/
        PermissionHelper.requestPermissionResult(this,requestCode,permissions);
    }
}
