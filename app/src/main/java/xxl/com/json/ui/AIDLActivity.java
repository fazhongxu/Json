package xxl.com.json.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import xxl.com.json.IUserInfoAIDL;
import xxl.com.json.R;
import xxl.com.json.service.UserInfoService;

public class AIDLActivity extends BaseActivity implements View.OnClickListener{

    private Button mBtnTest;
    private IUserInfoAIDL mUserInfoAIDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        initData();
        initView();
    }

    private void initData() {
        // 如果是两个应用通过AIDL跨进程通信 两个应用的AIDL文件必须一致，并且AIDL的报名要完全一致，否则会包Security Exception
        //跨进程通信时使用隐士意图（如果使用跨app通信）
       /* Intent intent = new Intent();
        intent.setAction("com.json.UserInfoService");
        intent.setPackage("xxl.com.json");*/
        Intent intent = new Intent(this, UserInfoService.class);
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务连接
             mUserInfoAIDL = IUserInfoAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务断开
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                try {
                    Toast.makeText(this, ""+ mUserInfoAIDL.getUsername()+"---"+mUserInfoAIDL.getAge(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

}
