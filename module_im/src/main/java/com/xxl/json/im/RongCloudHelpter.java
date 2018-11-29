package com.xxl.json.im;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import xxl.com.baselibray.BaseApp;

public class RongCloudHelpter {

    private static final String TAG = RongCloudHelpter.class.getSimpleName();

    private RongCloudHelpter() {
    }

    public static RongCloudHelpter getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final RongCloudHelpter INSTANCE = new RongCloudHelpter();
    }

    public void init(Application application) {
        RongIM.init(application);
    }

    public void init() {
        init(BaseApp.getInstance().getContext());
    }


    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link # init)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param //token 从服务端获取的用户身份令牌（Token）。
     * @param 。
     * @return RongIM  客户端核心类的实例。
     */
    public void connect() {
        // 获取token api
        Application context = BaseApp.getInstance().getContext();
        String token = "5WREKTcxH28fn42LCjotcsIMPL4IogNJLQA3Z89wDTsrqA+0uWAM0RZsSXwOOzelJfsjGrL9cdF0pfSGmkw2DA==";

        if (context.getApplicationInfo().packageName.equals(BaseApp.getCurProcessName(context.getApplicationContext()))) {

            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.e(TAG, "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.e(TAG, "--onSuccess" + userid);
                    context.startActivity(new Intent(context,ConversationActivity.class));
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e(TAG, "--onError" + errorCode);
                }
            });
        }
    }
}
