package xxl.com.json.recyclerview;

/**
 * Created by xxl on 2017/10/27.
 * 多条目布局类型支持
 */

public interface MultiTypeSupport<DATA> {
    /**
     * 根据返回的对象的不同来显示不同的列表类型
     *
     * @param data
     * @return
     */
    int getLayoutId(DATA data);
}
