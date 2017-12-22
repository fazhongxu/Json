package xxl.com.json.navigationbar;

import android.content.Context;
import android.view.View;

import xxl.com.json.R;

/**
 * Created by xxl on 2017/12/21.
 */

public class DefaultNavigationBar extends AbsNavigationBar {

    protected DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder<Builder>{

        public Builder(Context context, int layoutId) {
            super(context, layoutId);
        }

        public Builder setLeftText(CharSequence text){
            setText(R.id.tv_back,text);
            return this;
        }

        public Builder setLeftClickLisenter(View.OnClickListener lisenter){
            setOnClickLisenter(R.id.tv_back,lisenter);
            return this;
        }

        public Builder setLeftHidden(boolean visible){
            setVisible(R.id.tv_back,visible);
            return this;
        }

        public Builder setLeftIcon(int drawableId){
            setIcon(R.id.tv_back,drawableId);
            return this;
        }

        public Builder setTitle(CharSequence charSequence){
            setText(R.id.tv_title,charSequence);
            return this;
        }

        public Builder setRightText(CharSequence charSequence){
            setText(R.id.tv_right,charSequence);
            return this;
        }
        public Builder setRightClickLisenter(View.OnClickListener lisenter){
            setOnClickLisenter(R.id.tv_right,lisenter);
            return this;
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }

    }
}
