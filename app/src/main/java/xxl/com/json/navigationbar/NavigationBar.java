package xxl.com.json.navigationbar;

import android.content.Context;

/**
 * Created by xxl on 2017/12/21.
 */

public class NavigationBar extends AbsNavigationBar{

    protected NavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder<Builder>{

        public Builder(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
