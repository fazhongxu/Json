package xxl.com.json.common.okhttp;

/**
 * Created by xxl on 2017/12/28.
 * 上传进度监听 不写死进度，回调出总大小和当前进度
 */

public interface UpLoadListener {
    void progress(long totalSize,long currentLength);
}
