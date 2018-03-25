package xxl.com.json.ui.imageselect;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import xxl.com.json.R;
import xxl.com.json.commonadapter.CommonRecyclerViewAdapter;

/**
 * Created by xxl on 2018/3/25.
 * Email :
 * Description : 图片选择适配器
 */

public class ImageSelectAdapter extends CommonRecyclerViewAdapter<ImageBean> {
    public ImageSelectAdapter(Context context, List<ImageBean> data) {
        super(context, data, R.layout.item_image_select);
    }

    @Override
    protected void convert(ViewHolder holder, ImageBean data) {
        if (TextUtils.isEmpty(data.getPath())) {//显示相机
            holder.setVisible(R.id.ll_camera, View.VISIBLE);
            holder.setVisible(R.id.iv_image,View.INVISIBLE);
            holder.setVisible(R.id.iv_image_select_status,View.INVISIBLE);
        } else {//显示图片
            holder.setVisible(R.id.ll_camera, View.INVISIBLE);
            holder.setVisible(R.id.iv_image,View.VISIBLE);
            holder.setVisible(R.id.iv_image_select_status,View.VISIBLE);
           ImageView imageView = (ImageView) holder.getView(R.id.iv_image);

            Glide.with(mContext)
                    .load(data.getPath())
                    .into(imageView);
        }
    }
}
