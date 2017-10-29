package xxl.com.json;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xxl on 2017/10/27.
 */

public class ToastUtil {
    //考虑  可以修改图片，可以修改文字内容，可以修改Toast弹出时间，可以设置Toast弹出内容，可以自定义布局传入

    public static void showToast(Context context, int picDrawableRes, String toastMessage) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null, false);
        ImageView ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        ivPic.setBackground(ContextCompat.getDrawable(context, picDrawableRes));
        tvMessage.setText(toastMessage);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
