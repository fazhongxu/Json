package cn.sharesdk.onekeyshare.dialog;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.xxl.sharelib.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.google.GooglePlus;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by xxl on 2018/5/16.
 */

public class ShareDialog implements PlatformActionListener {
    private AlertDialog mDialog;
    private Context mContext;
    private GridView mGridView;
    private SimpleAdapter mAdapter;
    //分享的Icon图标
    private int[] mIcons = {R.drawable.ssdk_oks_classic_wechatmoments, R.drawable.ssdk_oks_classic_whatsapp, R.drawable.ssdk_oks_classic_qq, R.drawable.ssdk_oks_classic_facebook, R.drawable.ssdk_oks_classic_twitter, R.drawable.ssdk_oks_classic_googleplus, R.drawable.ssdk_oks_classic_email, R.drawable.ssdk_oks_classic_email};
    //分享的Icon下的Title  最好在String里面引用 方便国际化
    private String[] mTitle = {"Moments", "WeChat", "QQ", "FaceBook", "Twitter", "Gmail", "Email", "Copy Link"};
    //分享参数
    private ShareParams mShareParams;
    //右侧关闭按钮
    private ImageView mIvClose;

    private ShareDialog(Context context, ShareParams shareParams, OnShareListener shareListener) {
        this.mContext = context;
        this.mShareParams = shareParams;
        this.mShareListener = shareListener;
        initView();
        initParams();
        initEvent();
    }


    public static class Builder {
        private Context mContext;
        private ShareParams mShareParams;
        private ShareDialog.OnShareListener mShareListener;

        public Builder(Context context) {
            this.mContext = context;
            this.mShareParams = new ShareParams();
        }

        /**
         * 设置分享标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            if (title != null) {
                this.mShareParams.setTitle(title);
            }
            return this;
        }

        /**
         * 设置分享文本
         *
         * @param text
         * @return
         */
        public Builder setText(String text) {
            if (text != null) {
                this.mShareParams.setText(text);
            }
            return this;
        }

        /**
         * 设置分享图片地址
         *
         * @param imageUrl
         * @return
         */
        public Builder setImageUrl(String imageUrl) {
            if (imageUrl != null) {
                this.mShareParams.setImageUrl(imageUrl);
            }
            return this;
        }

        /**
         * 设置分享内容地址
         *
         * @param url
         * @return
         */
        public Builder setUrl(String url) {
            if (url != null) {
                this.mShareParams.setUrl(url);
            }
            return this;
        }

        /**
         * 设置点击后的链接 QQ需要
         *
         * @param titleUrl
         * @return
         */
        public Builder setTitleUrl(String titleUrl) {
            if (titleUrl != null) {
                this.mShareParams.setTitleUrl(titleUrl);
            }
            return this;
        }

        /**
         * 设置分享监听
         *
         * @param shareListener
         */
        public Builder setOnShareListener(ShareDialog.OnShareListener shareListener) {
            this.mShareListener = shareListener;
            return this;
        }

        public ShareDialog show() {
            return new ShareDialog(mContext, mShareParams, mShareListener);
        }

    }

    /**
     * 获取ShareParams 如果想自定义ShareParams 而不仅仅是通过Builder 建造者模式 也可通过Builder设置部分
     * 多余的通过此方法获取对象继续调用setShareParems设置分享参数
     *
     * @return
     */
    public ShareParams getShareParams() {
        if (mShareParams == null) {
            throw new IllegalStateException("Please show first!!!");
        }
        return this.mShareParams;
    }

    /**
     * 自定义方式 设置分享参数
     *
     * @param shareParems
     */
    public void setShareParems(ShareParams shareParems) {
        this.mShareParams = shareParems;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mDialog = new AlertDialog.Builder(mContext, R.style.CommonDialogStyle).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(attributes);
            window.setContentView(R.layout.ui_share_dialog);
            mGridView = (GridView) window.findViewById(R.id.gv_share);
            mIvClose = (ImageView) window.findViewById(R.id.iv_dialog_close);
        }
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        List<HashMap<String, Object>> shareList = new ArrayList<>();
        for (int i = 0; i < mIcons.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemImage", mIcons[i]);//添加图像资源的ID
            map.put("ItemText", mTitle[i]);//按序号做ItemText
            shareList.add(map);
        }
        mAdapter = new SimpleAdapter(mContext, shareList, R.layout.ui_share_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.iv_icon, R.id.tv_title});
        mGridView.setAdapter(mAdapter);
    }

    /**
     * 点击事件
     */
    private void initEvent() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String, Object> item = (HashMap<String, Object>) adapterView.getItemAtPosition(position);
                String itemText = (String) item.get("ItemText");
                Toast.makeText(mContext, "正在掉起" + itemText + "分享", Toast.LENGTH_SHORT).show();
                switch (itemText) {
                    case "WeChat"://微信
                        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                        wechat.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                        // 执行分享
                        wechat.share(mShareParams);
                        break;
                    case "Moments"://朋友圈
                        //3、非常重要：获取平台对象
                        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                        wechatMoments.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                        // 执行分享
                        wechatMoments.share(mShareParams);
                        break;
                    case "QQ"://QQ
                        Platform qq = ShareSDK.getPlatform(QQ.NAME);
                        qq.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                        // 执行分享
                        qq.share(mShareParams);
                        break;
                    case "FaceBook"://facebook
                        Platform faceBook = ShareSDK.getPlatform(Facebook.NAME);
                        faceBook.setPlatformActionListener(ShareDialog.this);
                        faceBook.share(mShareParams);
                        break;
                    case "Twitter"://Twitter
                        Platform twitter = ShareSDK.getPlatform(Twitter.NAME);
                        twitter.setPlatformActionListener(ShareDialog.this);
                        twitter.share(mShareParams);
                        break;
                    case "Gmail"://Gmail
                        Platform google = ShareSDK.getPlatform(GooglePlus.NAME);
                        google.setPlatformActionListener(ShareDialog.this);
                        google.share(mShareParams);
                        break;
                    case "Email"://Email
                        Platform email = ShareSDK.getPlatform(Email.NAME);
                        email.setPlatformActionListener(ShareDialog.this);
                        email.share(mShareParams);
                        break;
                    case "Copy Link"://link
                        Toast.makeText(mContext, "Copy link", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                dismiss();
            }
        });
    }

    /**
     * 取消分享点击事件
     *
     * @param Listener
     * @return
     */
    public ShareDialog setCancleClickListener(View.OnClickListener Listener) {
        mIvClose.setOnClickListener(Listener);
        dismiss();
        return this;
    }

    /**
     * 关闭对话框
     */
    private void dismiss() {
        mDialog.dismiss();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //分享完成
        if (mShareListener != null) {
            mShareListener.onSuccess(platform, i, hashMap);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        //分享错误
        if (mShareListener != null) {
            mShareListener.onFialure(platform, i, throwable);
        }
    }

    @Override
    public void onCancel(Platform platform, int i) {
        //分享取消
        if (mShareListener != null) {
            mShareListener.onCancle(platform, i);
        }
    }

    private OnShareListener mShareListener;

    /**
     * 设置分享监听
     *
     * @param shareListener
     */
    public ShareDialog setOnShareListener(OnShareListener shareListener) {
        this.mShareListener = shareListener;
        return this;
    }

    public interface OnShareListener {
        /**
         * 分享成功
         *
         * @param platform
         * @param i
         * @param hashMap
         */
        void onSuccess(Platform platform, int i, HashMap<String, Object> hashMap);

        /**
         * 分享失败
         *
         * @param platform
         * @param i
         * @param throwable
         */
        void onFialure(Platform platform, int i, Throwable throwable);

        /**
         * 分享取消
         *
         * @param platform
         * @param i
         */
        void onCancle(Platform platform, int i);
    }
}
