package xxl.com.json.mvp.demo1;

/**
 * Created by xxl on 2017/12/7.
 * V 层接口，定义P层和View层的方法
 */

public interface View {
    /**
     * 加载中
     */
    void loading();

    /**
     * 请求成功
     */
    void requestSucess(String result);

    /**
     * 请求失败
     */
    void requestFialure(String result);
}
