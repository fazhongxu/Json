package xxl.com.json.ui.imageselect;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xxl.com.json.R;
import xxl.com.json.ui.base.BaseActivity;

/**
 * 图片选择
 */
public class ImageSelectActivity extends BaseActivity implements View.OnClickListener {
    //需要使用图片选择的页面传递过来的参数
    //选择 多选 单选模式
    public static final String EXTRA_SELECT_MODE = "extra_select_mode";
    //最大选择数量
    public static final String EXTRA_MAX_COUNT = "extra_max_count";
    //是否需要显示相机
    public static final String EXTRA_CAMERA = "extra_camera";
    //已经选择好了的图片集合
    public static final String EXTRA_SELECT_LIST = "extra_select_list";
    //startActivityForResult时的requestCode
    public static final int REQUEST_CODE = 0x0012;
    //图片选择类型 单选
    public static final int SELECT_SINGLE = 0x0011;
    //多选
    public static final int SELECT_MULTI = 010022;
    //图片选择类型 单选 多选  默认多选
    private static int SELECT_MODE = SELECT_MULTI;
    //最多选择图片张数 默认9张
    private int MAX_COUNT = 9;
    //是否需要显示相机 默认true
    private boolean SHOW_CAMERA = true;
    //已经选好的图片集合
    private ArrayList mSelectList;
    //未选择的图片集合
    private List<ImageBean> mImageList = new ArrayList();
    private RecyclerView mRecyclerView;

    private static final int LOADER_ID = 1122;
    private TextView mTvPriview;
    private TextView mTvNum;
    private TextView mTvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_select);

        initView();

        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        mTvPriview = (TextView) findViewById(R.id.tv_preview);
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mTvOk = (TextView) findViewById(R.id.tv_ok);

        mTvPriview.setOnClickListener(this);
        mTvNum.setOnClickListener(this);
        mTvOk.setOnClickListener(this);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MAX_COUNT = extras.getInt(ImageSelectActivity.EXTRA_MAX_COUNT);
            SHOW_CAMERA = extras.getBoolean(ImageSelectActivity.EXTRA_CAMERA);
            SELECT_MODE = extras.getInt(ImageSelectActivity.EXTRA_SELECT_MODE);
            mSelectList = extras.getStringArrayList(ImageSelectActivity.EXTRA_SELECT_LIST);
            if (mSelectList == null) {
                mSelectList = new ArrayList();
            }
        }

        //获取图片列表
        getSupportLoaderManager().initLoader(LOADER_ID, null, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks mLoaderCallbacks = new LoaderManager.LoaderCallbacks() {
        String[] projection = new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID
        };

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            //查询
            CursorLoader cursorLoader = new CursorLoader(ImageSelectActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection
                    , projection[4] + ">0 AND " + projection[3] + "=? OR "
                    + projection[3] + "=? ", new String[]{"image/jpeg", "image/png"},
                    projection[3] + " DESC");
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader loader, Object data) {
            //查询结果
            if (data != null && data instanceof Cursor) {
                Cursor cursor = (Cursor) data;
                if (cursor.getCount() > 0) {
                    ImageBean image = new ImageBean();
                    if (SHOW_CAMERA){
                        image.setPath("");
                    }
                    mImageList.add(image);
                    while (cursor.moveToNext()) {
                        ImageBean imageBean = new ImageBean();
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(projection[0]));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(projection[1]));
                        Long date = cursor.getLong(cursor.getColumnIndexOrThrow(projection[2]));
                        Log.e("ImageSelectActivity", "onLoadFinished: " + path + "----" + name + "---" + date);
                        imageBean.setName(name);
                        imageBean.setPath(path);
                        imageBean.setTime(date);
                        mImageList.add(imageBean);

                        showImage(mImageList);
                    }
                }
            }

        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    /**
     * 图片列表展示
     * @param imageList
     */
    private void showImage(List<ImageBean> imageList){
        ImageSelectAdapter imageSelectAdapter = new ImageSelectAdapter(this, imageList);
        mRecyclerView.setAdapter(imageSelectAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_preview:
                break;
            case R.id.tv_num:
                break;
            case R.id.tv_ok:
                break;
        }
    }
}
