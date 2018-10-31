package xxl.com.baselibray.http.retrofit;

/**
 * Created by xxl on 2018/10/31.
 *
 * Description :
 */

public class HttpThrowable extends Throwable {
    private  String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static HttpThrowable handleThrowable(Throwable e) {
        if (e != null) {
            HttpThrowable httpThrowable = new HttpThrowable();
            httpThrowable.setMsg(e.getMessage());
            return httpThrowable;
        }
        return null;
    }


}
