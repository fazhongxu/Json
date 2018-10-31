package xxl.com.baselibray.http.retrofit;

/**
 * Created by xxl on 2018/10/31.
 * Description :
 */

public class HostImpl implements IHost {
    private static final String BASE_URL = "http://192.168.1.107:8080/OkHttpServer/";
    private static final String GITHUB = "https://github.com";
    @Override
    public String getBaseUrl(int type) {
        switch (type) {
            case Host.HOST:
                return BASE_URL;
            case Host.GITHUB:
                return GITHUB;
            default:
                break;
        }
        return "";
    }
}
