package xxl.com.baselibray.util;

/**
 * Created by xxl on 2018/11/6.
 * Description :
 */

public class Util {

    public static <T> T checkNotNull(T value,String message) {
        if (null == value) {
            throw new NullPointerException(message);
        }
        return value;
    }
}
