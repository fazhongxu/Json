package xxl.com.json.common;

import android.content.Context;

import java.util.Map;

import xxl.com.baselibray.http.HttpCallBackEngine;

/**
 * Created by xxl on 2017/10/24.
 */

public abstract class HttpCallBack<T> implements HttpCallBackEngine {

    @Override
    public void onPreExcute(Context context, Map<String, Object> params) {
        //请求之前回调此方法带入params,可以添加需要的接口默认或者必须的params
        params.put("time","");
        params.put("devices","OPPO R9s");
        params.put("system","android,6.0.1");
        params.put("version","1.2.9");
        params.put("unique_id","");
        params.put("client_id","");
        onPreExcute();
    }

    //请求之前能调用此方法
    public void onPreExcute() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(String result) {//请求成功将数据解析成javaBean对象
        //根据泛型获取class
//        Class<T> entityClass =
//                (Class<T>) ((ParameterizedType)
//                        getClass().getGenericSuperclass())
//                        .getActualTypeArguments()[0];
//        Gson gson = new Gson();
//        T resultBean = gson.fromJson(result, entityClass);
        onSucces(result);
    }

    public abstract void onSucces(String resultBean);
}
