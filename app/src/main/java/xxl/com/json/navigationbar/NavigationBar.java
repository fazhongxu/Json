package xxl.com.json.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

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

        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId,parent);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
