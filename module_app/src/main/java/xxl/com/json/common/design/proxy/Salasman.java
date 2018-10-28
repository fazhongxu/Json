package xxl.com.json.common.design.proxy;

/**
 * Created by xxl on 2018/1/4.
 */

public class Salasman implements IBank {
    private IBank mIBank;//代理者持有被代理对象
    public Salasman(IBank iBank){
        this.mIBank = iBank;
    }
    @Override
    public void applyBank() {
        mIBank.applyBank();//调用被代理对象的方法
    }
}
