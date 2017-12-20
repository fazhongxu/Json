package xxl.com.json.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by xxl on 2017/12/20.
 */

public class NavigationBar extends AbsNavigationBar{

    protected NavigationBar(AbsNavigationBar.Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder{

        public Builder(Context context, ViewGroup parent, int layoutId) {
            super(context, parent, layoutId);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
