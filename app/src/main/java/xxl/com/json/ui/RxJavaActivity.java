package xxl.com.json.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import xxl.com.json.R;
import xxl.com.json.ui.base.BaseActivity;

public class RxJavaActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        //给图片添加水印
        Observable.just("http://img.taopic.com/uploads/allimg/130824/240413-130R40T23089.jpg")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        URL url = new URL(s);
                        URLConnection urlConnection = url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Bitmap waterBitmap = drawWaterBitmap(bitmap, "RxJava");
                        return waterBitmap;
                    }
                })
                .subscribeOn(Schedulers.io())//上游发送事件的线程
                .observeOn(AndroidSchedulers.mainThread())//下游接收事件的线程
                .subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                mImageView.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * 给图片绘制水印
     */
    private Bitmap drawWaterBitmap(Bitmap bitmap, String text) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Paint paint = new Paint();
        paint.setTextSize(180);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setDither(true);

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.drawText(text, 0, height /2, paint);
        return bmp;
    }

}
