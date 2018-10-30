package com.xxl.modelweb;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.mediatorweb.IConstantWeb;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = IConstantWeb.WEB_SIMPLE_WEB)
public class SimpleWebViewActivity extends AppCompatActivity {

    @BindView(R2.id.web_refresh_rfwv)
    RefreshWebView webRefreshRfwv;
    private RefreshWebView mRefreshWebView;
    private String mUrl;

    public static String getUrl() {
        return "http://xxxx";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web_view);
        ButterKnife.bind(this);
        mRefreshWebView = findViewById(R.id.web_refresh_rfwv);
        SimpleWebView webView = mRefreshWebView.getWebView();
        mUrl = getIntent().getStringExtra(IConstantWeb.URL);

        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl);
        }
    }
}
