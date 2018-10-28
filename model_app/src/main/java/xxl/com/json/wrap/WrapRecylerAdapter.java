package xxl.com.json.wrap;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by xxl on 2017/11/8.
 * 装饰设计模式包装recyclerView的Adapter,
 * 使adapter支持添加头部和底部
 * <p>
 * 装饰设计模式 在使用继承的情况下拓展功能
 */

public class WrapRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mAdaper;

    private ArrayList<View> mHeaderViews;
    private ArrayList<View> mFooterViews;

    public WrapRecylerAdapter(RecyclerView.Adapter adapter) {
        this.mAdaper = adapter;
        this.mHeaderViews = new ArrayList<>();
        this.mFooterViews = new ArrayList<>();
        mAdaper.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {//当数据刷新的时候通知刷新界面
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {//position就是itemType
        //头部返回头部的ViewHolder
        //Adapter返回Adapter的ViewHolder
        //底部返回底部的ViewHolder
        int headerViewCount = getHeaderViewCount();
        if (position < headerViewCount) {//判断当前位置是否小于headerView的数量，如果小于，说明当前位置是头部布局所处位置，应该创建头布局
            return createHeaderViewFooterViewHolder(mHeaderViews.get(position));
        }
        int adjPosition = position - headerViewCount;
        int itemCount = 0;
        if (mAdaper != null) {
            itemCount = mAdaper.getItemCount();//adpter真实的数据数量，不包括头部和底部数量
            if (adjPosition < itemCount) {//如果当前位置 - 头部数量小于adapter的数量，则表示是adapter所处位置，应该创建adapter条目布局
                return mAdaper.onCreateViewHolder(parent, mAdaper.getItemViewType(adjPosition));
            }
        }
        return createHeaderViewFooterViewHolder(mFooterViews.get(adjPosition - itemCount));
    }

    /**
     * 创建头部或者底部的布局的ViewHolder
     */
    private RecyclerView.ViewHolder createHeaderViewFooterViewHolder(View view) {
        //创建就可以了，不需要传递设置数据
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        //头部的不需要绑定数据
        int headerViewCount = getHeaderViewCount();
        if (position < headerViewCount) {
            return;
        }
        int itemCount = 0;
        if (mAdaper != null) {
            int adjPosition = position - headerViewCount;
            itemCount = mAdaper.getItemCount();
            if (adjPosition < itemCount) {
                mAdaper.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //直接返回position  根据position位置判断条目类型
        return position;
    }

    @Override
    public int getItemCount() {
        return getHeaderViewCount() + mAdaper.getItemCount() + getFooterViewCount();
    }

    /**
     * 添加头部
     */
    public void addHeaderView(View view) {
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加底部
     */
    public void addFooterView(View view) {
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除头部
     */
    public void removeHeaderView(View view) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除底部
     */
    public void removeFooterView(View view) {
        if (mFooterViews.contains(view)) {
            mFooterViews.remove(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取头部View数量
     */
    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    /**
     * 获取底部View数量
     */
    public int getFooterViewCount() {
        return mFooterViews.size();
    }

}
