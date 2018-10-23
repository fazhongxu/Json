package xxl.com.json.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by xxl on 2018/10/23.
 *
 * Description :
 */

public class MultiImageView extends LinearLayout {
    private List<String> mImageUrls;
    private int mImageWidth;
    public MultiImageView(Context context) {
        this(context,null);
    }

    public MultiImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultiImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);

        init();
    }

    private void init() {
        mImageUrls = new ArrayList<>();
        mImageWidth = dp2px(24);
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    public void setImageUrls(List<String> imageUrls) {
        this.mImageUrls = imageUrls;
        requestLayout();
    }

    public void setImageUrls(String[] imageUrls) {
        this.mImageUrls = Arrays.asList(imageUrls);
        requestLayout();
    }


    private ImageView createImageView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 控件总宽度
        int width = 0;
        if (mImageUrls.size() >0) {
            width += mImageWidth;
            width += (mImageWidth / 2) * (mImageUrls.size() -1);
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int left = 0;
        int right = 0;
        for (int i = 0; i < mImageUrls.size(); i++) {
            if (i == 0) {
                right = left + mImageWidth;
            }else {
                left =  (mImageWidth / 2) * i;
                right = left + mImageWidth;
            }
            ImageView imageView = createImageView();
            addView(imageView);
            imageView.layout(left,0,right,mImageWidth);
            //圆形图片
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(this).load(mImageUrls.get(i)).apply(requestOptions).into(imageView);
        }
    }

}
