package xxl.com.json.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import xxl.com.json.IAidlProcessGuard;

/**
 * Created by xxl on 2017/12/14.
 * 双进程守护的服务 普通服务
 */

public class NormalService extends Service {
    private static final String TAG = "NormalService";

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, "run: 线程正在运行中");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //置为可见进程，提高优先级，防止提前被杀死
        startForeground(1, new Notification());

        //绑定另个一进程 实现双进程守护
        bindService(new Intent(this,GuardService.class),mConn, Context.BIND_AUTO_CREATE);
        return START_STICKY;
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接建立
            Log.e("NormalService", "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接
            //断开连接的时候 重启服务 连接服务
            startService(new Intent(NormalService.this,GuardService.class));

            bindService(new Intent(NormalService.this,GuardService.class),mConn,Context.BIND_AUTO_CREATE);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //绑定Aidl binder 进程间通信
        return new IAidlProcessGuard.Stub() {
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
