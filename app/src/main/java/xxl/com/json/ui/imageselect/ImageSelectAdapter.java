package xxl.com.json.ui.imageselect;

import android.content.Context;

import java.util.List;

import xxl.com.json.R;
import xxl.com.json.commonadapter.CommonRecyclerViewAdapter;

/**
 * Created by xxl on 2018/3/25.
 * Email :
 * Description : 图片选择适配器
 */

public class ImageSelectAdapter extends CommonRecyclerViewAdapter<ImageBean>{
    public ImageSelectAdapter(Context context, List<ImageBean> data) {
        super(context, data, R.layout.custom_layout_countdown);
    }

    @Override
    protected void convert(ViewHolder holder, ImageBean data) {

    }
}
