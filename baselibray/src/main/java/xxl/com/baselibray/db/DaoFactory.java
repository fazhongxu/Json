package xxl.com.baselibray.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by xxl on 2018/1/17.
 */

public class DaoFactory {
    private volatile static DaoFactory sDaoFactory;
    private SQLiteDatabase mSqLiteDatabase;
    private static final String mDbPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "json" + File.separator + "database";

    public DaoFactory() {
        // 6.0权限 判断是否挂载
        File dir = new File(mDbPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "json.db");

        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
    }

    public static DaoFactory getInstance() {
        if (sDaoFactory == null) {
            synchronized (DaoFactory.class) {
                if (sDaoFactory == null) {
                    sDaoFactory = new DaoFactory();
                }
            }
        }
        return sDaoFactory;
    }

    public <C> IDaoSupport<C> getFactory(Class<C> clazz) {
        DaoSupport daoSupport = new DaoSupport();
        daoSupport.init(mSqLiteDatabase, clazz);
        return daoSupport;
    }
}
