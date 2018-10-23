package com.xxl.modelweb;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.mediatorweb.IConstantWeb;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = IConstantWeb.WEB_SIMPLE_WEB)
public class SimpleWebViewActivity extends AppCompatActivity {

    private RefreshWebView mRefreshWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web_view);
        mRefreshWebView = findViewById(R.id.web_refresh_rfwv);
        SimpleWebView webView = mRefreshWebView.getWebView();
        mUrl = getIntent().getStringExtra(IConstantWeb.URL);

        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl);
        }
    }
}
