package xxl.com.baselibray.http.retrofit;

public interface DownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);
}
