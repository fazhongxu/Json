package xxl.com.json.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by xxl on 2017/12/28.
 */

public class NetWorkUtils {
    public static boolean isNetWorkConnected(Context context) {
        Context applicationContext = context.getApplicationContext();
        ConnectivityManager connectivityManager = (ConnectivityManager)
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable();
        }

        return false;
    }
}
