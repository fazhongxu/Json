package xxl.com.json.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;
import xxl.com.baselibray.http.HttpUtil;
import xxl.com.json.R;
import xxl.com.json.bannerview.BannerAdapter;
import xxl.com.json.bannerview.BannerView;
import xxl.com.json.bean.JokeBean;
import xxl.com.json.ui.base.BaseActivity;

public class BannerActivity extends BaseActivity {

    private static final String TAG = "BannerActivity";
    private BannerView mBannerView;
    private BannerView mBannerViewBelow;

    private int[] mBanners = new int[]{R.mipmap.ic_banner_001, R.mipmap.ic_banner_002,
            R.mipmap.ic_banner_003, R.mipmap.ic_banner_004, R.mipmap.ic_banner_005};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
        //initData();
        mBannerView.post(new Runnable() {//设置了宽高比 所以需要post 否则拿不到宽度 显示不出图片
            @Override
            public void run() {
                setBanner(mBannerView);
            }
        });

        mBannerViewBelow.post(new Runnable() {//设置了宽高比 所以需要post 否则拿不到宽度 显示不出图片
            @Override
            public void run() {
                setBanner(mBannerViewBelow);
            }
        });
    }

    private void initView() {
        mBannerView = (BannerView) findViewById(R.id.banner_view);
        mBannerViewBelow = (BannerView) findViewById(R.id.banner_view_below);
    }

    private void setBanner(BannerView bannerView) {
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(BannerActivity.this);
                } else {
                    imageView = (ImageView) convertView;
                }

                Glide.with(BannerActivity.this)
                        .load(mBanners[position])
                        .into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return mBanners.length;
            }
        });

        bannerView.startScroll();
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
                                if (rotate_banner == null) {
                                    return;
                                }
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
            public View getView(int position, View convertView) {
                ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(BannerActivity.this);
                } else {
                    imageView = (ImageView) convertView;
                }
                Glide.with(BannerActivity.this)
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

}
