package com.xxl.modelweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/web/simple/web")
public class SimpleWebViewActivity extends AppCompatActivity {

    private RefreshWebView mRefreshWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web_view);
        mRefreshWebView = findViewById(R.id.web_refresh_rfwv);
        SimpleWebView webView = mRefreshWebView.getWebView();
        mUrl = getIntent().getStringExtra("url");

        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl);
        }

        ARouter.getInstance().build("/test/web").navigation();
    }
}
