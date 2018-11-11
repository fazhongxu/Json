package com.xxl.modelweb;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by xxl on 2018/10/17.
 *
 * Description : SimpleWebView
 */

public class SimpleWebView extends WebView {

    public SimpleWebView(Context context) {
        super(context);
    }

    public SimpleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChanged(l,t,oldl,oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public OnScrollChangeListener mOnScrollChangeListener;

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener{
       void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
