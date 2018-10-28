package xxl.com.json.ui.imageselect;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import xxl.com.json.R;
import xxl.com.json.commonadapter.CommonRecyclerViewAdapter;

/**
 * Created by xxl on 2018/3/25.
 * Email :
 * Description : 图片选择适配器
 */

public class ImageSelectAdapter extends CommonRecyclerViewAdapter<String> {
    private List<String> mSelectList;
    private int mMaxCount;

    public ImageSelectAdapter(Context context, List<String> item, List<String> selectList, int maxCount) {
        super(context, item, R.layout.item_image_select);
        this.mSelectList = selectList;
        this.mMaxCount = maxCount;
    }

    @Override
    protected void convert(ViewHolder holder, final String item) {
        if (TextUtils.isEmpty(item)) {//显示相机
            holder.setVisible(R.id.ll_camera, View.VISIBLE);
            holder.setVisible(R.id.iv_image, View.INVISIBLE);
            holder.setVisible(R.id.iv_image_select_status, View.INVISIBLE);
        } else {//显示图片
            holder.setVisible(R.id.ll_camera, View.INVISIBLE);
            holder.setVisible(R.id.iv_image, View.VISIBLE);
            holder.setVisible(R.id.iv_image_select_status, View.VISIBLE);
            ImageView imageView = (ImageView) holder.getView(R.id.iv_image);
            ImageView ivSelectStatus = (ImageView) holder.getView(R.id.iv_image_select_status);

            Glide.with(mContext)
                    .load(item)
                    .into(imageView);

            if (mSelectList.contains(item)) {//包含在选中的集合中 则点亮对勾
                ivSelectStatus.setSelected(true);
            } else {
                ivSelectStatus.setSelected(false);
            }

            holder.setOnClickLisenter(R.id.iv_image, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击图片 添加到选中的集合内 刷新adapter
                    //如果图片不包含在选中的集合内 点击就添加  否则就移除
                    //刷新Adapter
                    if (!mSelectList.contains(item)) {
                        if (mSelectList.size() >= mMaxCount) {
                            Toast.makeText(mContext, "最多能选择" + mMaxCount + "张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mSelectList.add(item);
                    } else {
                        mSelectList.remove(item);
                    }
                    notifyDataSetChanged();

                    if (mListener != null) {//监听点击回调
                        mListener.select();
                    }
                }
            });
        }
    }

    private ImageSelectListener mListener;

    /**
     * 图片选择监听
     * @param listener
     */
    public void setImageSelectListener(ImageSelectListener listener) {
        this.mListener = listener;
    }
}
