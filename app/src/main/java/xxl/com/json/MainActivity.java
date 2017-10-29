package xxl.com.json;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;
import xxl.com.baselibray.http.HttpUtil;
import xxl.com.json.bean.JokeBean;
import xxl.com.json.viewpager.BannerAdapter;
import xxl.com.json.viewpager.BannerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTest;
    private BannerView mBannerView;
    private String TAG = "MainActivity";

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

//        mViewPager.setAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                ToastUtil.showToast(this, R.mipmap.ic_launcher, "Toast内容");
                break;
            default:
                break;
        }
    }
}
