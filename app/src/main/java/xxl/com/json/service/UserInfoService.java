package xxl.com.json.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;

import xxl.com.json.IAidlUserInfo;

/**
 * Created by xxl on 2017/12/14.
 */

public class UserInfoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public IAidlUserInfo.Stub mBinder = new IAidlUserInfo.Stub() {
        @Override
        public String getUsername() throws RemoteException {
            return "json";
        }

        @Override
        public String getAge() throws RemoteException {
            return "18";
        }
    };
}

