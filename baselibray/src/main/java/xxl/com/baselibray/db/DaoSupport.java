package xxl.com.baselibray.db;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

/**
 * Created by xxl on 2018/1/17.
 */

public class DaoSupport<D> implements IDaoSupport<D> {
    private SQLiteDatabase mSqLiteDatabase;
    private Class<?> mClass;
    public void init(SQLiteDatabase sqLiteDatabase, Class<?> clazz) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClass = clazz;

        //创建数据库表
        /* CREATE TABLE Student(_id integer primary key autoincrement,
         name text,
         age text,
         collage varchar);*/
        StringBuffer sb = new StringBuffer();

        sb.append("CREATE TABLE")
                .append(mClass.getSimpleName())
                .append("(_id integer primary key autoincrement,");

        Field[] fields = mClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType().getComponentType();
            String name = field.getName();
        }


        sqLiteDatabase.execSQL("");
    }

    @Override
    public long insert(D d) {
        return 0;
    }
}
