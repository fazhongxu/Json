package com.xxl.modelweb;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by xxl on 2018/10/17.
 * <p>
 * Description : RefreshWebView
 */

public class RefreshWebView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener,
        SimpleWebView.OnScrollChangeListener {

    private SimpleWebView mSimpleWebView;
    private RefreshWebViewClient mRefreshWebViewClient = new RefreshWebViewClient();

    public RefreshWebView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ui_simple_refreshwebview, this);
        init();
    }

    private void init() {
        mSimpleWebView = findViewById(R.id.ui_web_swv);
        setOnRefreshListener(this);
        mSimpleWebView.setOnScrollChangeListener(this);
        mSimpleWebView.setWebViewClient(mRefreshWebViewClient);
    }

    @Override
    public void onRefresh() {
        mSimpleWebView.reload();
    }

    public SimpleWebView getWebView() {
        return mSimpleWebView;
    }

    public RefreshWebViewClient getWebViewClient() {
        return mRefreshWebViewClient;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mSimpleWebView.getScrollY() == 0) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    public class RefreshWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            setRefreshing(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setRefreshing(false);
        }
    }
}
