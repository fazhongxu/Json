package xxl.com.baselibray.http;

import java.util.Map;

/**
 * Created by xxl on 2017/10/23.
 * http网络请求引擎规范
 */

public interface IHttpEngine {
    //get请求
    void get(String url, Map<String, Object> params, HttpCallBackEngine callBack);

    //post请求
    void post(String url, Map<String, Object> params, HttpCallBackEngine callBack);
    //文件上传

    //文件下载

    //https证书添加
}
