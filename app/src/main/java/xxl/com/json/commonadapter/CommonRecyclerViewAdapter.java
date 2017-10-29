package xxl.com.json.commonadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xxl on 2017/10/26.
 * 多适配的RecyclerViewAdapter
 */

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewAdapter.ViewHolder> {
    //考虑 数据怎么处理? 布局怎么处理? 如何绑定?
    //布局直接传入资源layoutId,数据用泛型，绑定用抽象方法回传给实现类
    private Context mContext;
    private List<T> mDatas;
    private int layoutId;
    private LayoutInflater mLayoutInflater;
    private MultiTypeSupport<T> mMultiTypeSupport;

    public CommonRecyclerViewAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mDatas = data;
        this.layoutId = layoutId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 多条目布局适配
     *
     * @param context
     * @param data
     * @param multiTypeSupport
     */
    public CommonRecyclerViewAdapter(Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;//对象的Adapter设计模式
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            layoutId = viewType;
        }
        View view = mLayoutInflater.inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //利用抽象类回传数据给实现类
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    protected abstract void convert(ViewHolder holder, T data);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> viewSparseArray;

        public ViewHolder(View itemView) {
            super(itemView);
            viewSparseArray = new SparseArray<>();
        }

        //提供方法方便子类holder调用

        /**
         * 设置文字
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, CharSequence text) {
            TextView textView = findView(viewId);
            textView.setText(text);
            return this;
        }

        /**
         * 设置控件显示或隐藏
         *
         * @param viewId
         * @param visible
         * @return
         */
        public ViewHolder setVisible(int viewId, boolean visible) {
            findView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
            return this;
        }


        /**
         * 设置点击事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setOnClickLisenter(int viewId, View.OnClickListener listener) {
            findView(viewId).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置Image资源
         *
         * @param viewId
         * @param resourceId
         * @return
         */
        public ViewHolder setImage(int viewId, int resourceId) {
            ImageView imageView = findView(viewId);
            imageView.setImageResource(resourceId);
            return this;
        }


        /**
         * 设置长按点击事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setOnLongClickLisenter(int viewId, View.OnLongClickListener listener) {
            findView(viewId).setOnLongClickListener(listener);
            return this;
        }


        //减少findViewById次数
        public <T extends View> T findView(int viewId) {
            //先从缓存中找
            View view;
            view = viewSparseArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                if (view != null) {
                    //存入到集合中缓存
                    viewSparseArray.put(viewId, view);
                }
            }
            return (T) view;
        }
    }

}
