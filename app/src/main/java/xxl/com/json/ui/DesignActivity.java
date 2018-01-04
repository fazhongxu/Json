package xxl.com.json.ui;

import android.os.Bundle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import xxl.com.json.R;
import xxl.com.json.common.design.proxy.IBank;
import xxl.com.json.common.design.proxy.Man;

public class DesignActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        //静态代理
       /* Man json = new Man("json");
        Salasman salasman = new Salasman(json);
        salasman.applyBank();*/

        //动态代理
        Man jsonjson = new Man("jsonjson");
        BankInvocationHandler proxyHandler = new BankInvocationHandler(jsonjson);
        IBank iBank = (IBank) Proxy.newProxyInstance(jsonjson.getClass().getClassLoader(),
                new Class[]{IBank.class}, proxyHandler);

        iBank.applyBank();
    }

    private class BankInvocationHandler implements InvocationHandler {
        private Object object;//持有被代理对象

        public BankInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(object, args);
        }
    }
}
