package xxl.com.json.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xxl on 2017/11/8.
 */

public class WrapRecyclerView extends RecyclerView {
    //支持添加头部和底部的recyclerview
    private WrapRecylerAdapter mAdapter;

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {//设置为自己的包装过的Adapter
        mAdapter= new WrapRecylerAdapter(adapter);
        super.setAdapter(mAdapter);
    }

    /**
     * 添加头部
     */
    public void addHeaderView(View view) {
        if (mAdapter!= null){
            mAdapter.addHeaderView(view);
        }
    }

    /**
     * 添加底部
     */
    public void addFooterView(View view) {
        if (mAdapter!= null){
            mAdapter.addFooterView(view);
        }
    }

    /**
     * 移除头部
     */
    public void removeHeaderView(View view) {
        if (mAdapter!= null){
            mAdapter.removeHeaderView(view);
        }
    }

    /**
     * 移除底部
     */
    public void removeFooterView(View view) {
        if (mAdapter!= null){
            mAdapter.removeFooterView(view);
        }
    }
}
