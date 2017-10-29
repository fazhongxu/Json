package xxl.com.baselibray.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xxl on 2017/10/23.
 */

public class HttpUtil {
    //默认引擎
    private static IHttpEngine mHttpEngine;
    //请求url地址
    private String mUrl;

    private Map<String, Object> mParam;

    //请求方式,设置默认为get
    private int REQUEST_TYPE = GET;

    //get请求
    private static final int GET = 0x0011;
    //post请求
    private static final int POST = 0x0022;

    private Context mContext;

    //请求参数
    //回调
    private HttpUtil(Context context) {
        this.mContext = context;
        mParam = new HashMap<>();
    }

    /**
     * 初始化引擎
     */
    public static void init() {
        mHttpEngine = new OkHttpEngine();
    }

    /**
     * 切换引擎
     */
    public HttpUtil exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    public static HttpUtil with(Context context) {
        return new HttpUtil(context);
    }

    public HttpUtil url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 添加单个参数
     *
     * @param key
     * @param value
     * @return
     */
    public HttpUtil addParam(String key, Object value) {
        this.mParam.put(key, value);
        return this;
    }

    /**
     * 添加多个参数
     *
     * @param params
     * @return
     */
    public HttpUtil addParams(Map<String, Object> params) {
        this.mParam.putAll(params);
        return this;
    }

    /**
     * 有返回结果的执行方法
     */
    public HttpUtil execute(HttpCallBackEngine callBack) {
        if (callBack == null) {
            callBack = HttpCallBackEngine.DEFAULT_CALL_BACK;
        }

        callBack.onPreExcute(mContext, mParam);

        if (REQUEST_TYPE == GET) {
            get(mUrl, mParam, callBack);
        }

        if (REQUEST_TYPE == POST) {
            post(mUrl, mParam, callBack);
        }
        return this;
    }

    /**
     * 不需要返回结果的执行
     */
    public HttpUtil execute() {
        execute(null);
        return this;
    }

    public HttpUtil get() {
        REQUEST_TYPE = GET;
        return this;
    }

    public HttpUtil post() {
        REQUEST_TYPE = POST;
        return this;
    }

    private HttpUtil get(String url, Map<String, Object> params, HttpCallBackEngine callBack) {
        mHttpEngine.get(url, params, callBack);
        return this;
    }

    private HttpUtil post(String url, Map<String, Object> params, HttpCallBackEngine callBack) {
        mHttpEngine.post(url, params, callBack);
        return this;
    }

    /**
     * 拼接请求地址
     *
     * @param url
     * @param params
     * @return
     */
    public static String joinParams(String url, Map<String, Object> params) {
        if (params == null || params.size() < 0) {
            return url;
        }
        StringBuffer sb = new StringBuffer(url);
        if (!url.contains("?")) {
            sb.append("?");
        }
        if (!url.endsWith("&")) {
            sb.append("&");
        }
        for (Map.Entry<String,Object> entry : params.entrySet()) {
            sb.append(entry.getKey()+"="+entry.getValue()+"&");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();

    }

}
