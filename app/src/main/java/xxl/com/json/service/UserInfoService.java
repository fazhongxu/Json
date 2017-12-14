package xxl.com.json.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import xxl.com.json.IUserInfoAIDL;

/**
 * Created by xxl on 2017/12/14.
 */

public class UserInfoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public IUserInfoAIDL.Stub mBinder = new IUserInfoAIDL.Stub() {
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

