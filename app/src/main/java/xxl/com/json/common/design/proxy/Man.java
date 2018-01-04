package xxl.com.json.common.design.proxy;

import android.util.Log;

/**
 * Created by xxl on 2018/1/4.
 */

public class Man implements IBank {
    private String mName;
    public Man(String name){
        this.mName = name;
    }
    @Override
    public void applyBank() {
        Log.e("aaa", mName+"申请办卡...");
    }
}
