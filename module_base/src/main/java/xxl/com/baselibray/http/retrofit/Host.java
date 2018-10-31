package xxl.com.baselibray.http.retrofit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by xxl on 2018/10/31.
 *
 * Description :
 */
@IntDef({Host.HOST,Host.GITHUB})
@Retention(RetentionPolicy.SOURCE)
public @interface Host {
    int HOST = 0;
    int GITHUB = 1;
}
