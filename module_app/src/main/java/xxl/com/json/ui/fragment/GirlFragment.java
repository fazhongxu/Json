package xxl.com.json.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import xxl.com.json.R;
import xxl.com.json.ui.base.BaseFragment;

/**
 * Created by xxl on 2018/1/23.
 */

public class GirlFragment extends BaseFragment {
    private static final String TYPE_KEY = "type_key";
    private static final String TYPE_DES_KEY = "type_des_key";
    private int mType;
    private String[] mDes;
    private TextView mTvDes;

    public static GirlFragment newInstance(int type, String[] des) {
        GirlFragment girlFragment = new GirlFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        bundle.putSerializable(TYPE_DES_KEY, des);
        girlFragment.setArguments(bundle);
        return girlFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(TYPE_KEY);
            mDes = (String[]) arguments.getSerializable(TYPE_DES_KEY);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girl;
    }

    @Override
    protected void initView(View view) {
        mTvDes = (TextView) view.findViewById(R.id.tv_des);
    }


    @Override
    protected void initData() {
        switch (mType) {
            case 0:
                mTvDes.setText(mDes[0]);
                break;
            case 1:
                mTvDes.setText(mDes[1]);
                break;
            case 2:
                mTvDes.setText(mDes[2]);
                break;
            case 3:
                mTvDes.setText(mDes[3]);
                break;
            case 4:
                mTvDes.setText(mDes[4]);
                break;
            case 5:
                mTvDes.setText(mDes[5]);
                break;
            case 6:
                mTvDes.setText(mDes[6]);
                break;
            case 7:
                mTvDes.setText(mDes[7]);
                break;
            default:
                break;
        }
    }
}
