package xxl.com.baselibray.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

        sb.append("create table if not exists ")
                .append(DBUtil.getTableName(mClass))
                .append("(_id integer primary key autoincrement,");

        Field[] fields = mClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String type = field.getType().getSimpleName();
            String name = field.getName();
            sb.append(name)
                    .append(" ")
                    .append(DBUtil.getColumnType(type))
                    .append(",");
        }

        sb.replace(sb.length() - 1, sb.length(), ")");

        String tableCreateSql = sb.toString();
        Log.e("aaa", "init: " + tableCreateSql);
        sqLiteDatabase.execSQL(tableCreateSql);
    }

    @Override
    public long insert(D d) {
        ContentValues contentValues = getContentValues(d);
        return mSqLiteDatabase.insert(DBUtil.getTableName(d.getClass()),null,contentValues);
    }

    private ContentValues getContentValues(Object object) {
        ContentValues values = new ContentValues();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(object);
                Method method = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                method.invoke(values,key,value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
