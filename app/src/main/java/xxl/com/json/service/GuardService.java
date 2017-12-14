package xxl.com.json.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import xxl.com.json.IAidlProcessGuard;

/**
 * Created by xxl on 2017/12/14.
 * 双进程守护的 守护进程服务
 */

public class GuardService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(2, new Notification());
        //绑定服务 建立连接 守护另一个进程服务
        bindService(new Intent(this,NormalService.class),mConn, Context.BIND_AUTO_CREATE);
        return START_STICKY;
    }
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //建立连接
            Log.e("GuardService", "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //连接断开
            //开启服务 重启另一个服务
            startService(new Intent(GuardService.this,NormalService.class));

            bindService(new Intent(GuardService.this,NormalService.class),mConn,Context.BIND_AUTO_CREATE);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAidlProcessGuard.Stub() {};
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
