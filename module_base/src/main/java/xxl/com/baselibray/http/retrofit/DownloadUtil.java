package xxl.com.baselibray.http.retrofit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xxl on 2018/10/31.
 * <p>
 * Description :
 */

public class DownloadUtil {

    public static Observable<String> download(String url, String filePath) {
        return RetrofitClient
                .getInstance()
                .getRetrofit()
                .create(IDownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .map(responseBody -> writeFile(responseBody.byteStream(), filePath))
                .onErrorReturn(Throwable::getMessage);

    }

    private static String writeFile(InputStream is, String filePath) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            byte[] buff = new byte[8*1024];
            int len;
            while ((len = is.read(buff)) != -1) {
                fos.write(buff,0,len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        return filePath;
    }

}
