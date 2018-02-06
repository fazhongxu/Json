package xxl.com.json.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by xxl on 2017/11/22.
 */

public class SharedPreferenceUtils {
    private static SharedPreferenceUtils mInstance;

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEdior;

    private SharedPreferenceUtils() {

    }

    public static SharedPreferenceUtils getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferenceUtils.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferenceUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文，这里用的是context.getApplicationContext()
     *                避免使用Activity的上下文导致activity没法回引起内存泄漏
     * @return
     */
    public static SharedPreferences init(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return mSharedPreferences;
    }

    /**
     * 存值
     *
     * @param key
     * @param object
     */
    public void saveParam(String key, Object object) {
        if (mSharedPreferences == null) {
            throw new RuntimeException("Please call init Method first!");
        }
        if (mEdior == null) {
            mEdior = mSharedPreferences.edit();
        }
        String type = object.getClass().getSimpleName();
        if ("String".equals(type)) {
            mEdior.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            mEdior.putInt(key, (Integer) object);
        } else if ("Float".equals(type)) {
            mEdior.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            mEdior.putLong(key, (Long) object);
        } else if ("Boolean".equals(type)) {
            mEdior.putBoolean(key, (Boolean) object);
        } else {
            throw new IllegalArgumentException("Current does not support this type!");
        }
        mEdior.commit();
    }

    /**
     * 获取存储的值
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getParam(String key, Object defaultValue) {
        if (mSharedPreferences == null) {
            throw new RuntimeException("Please call init Method first!");
        }
        String type = defaultValue.getClass().getSimpleName();
        if ("String".equals(type)) {
            return mSharedPreferences.getString(key, (String) defaultValue);
        } else if ("Integer".equals(type)) {
            return mSharedPreferences.getInt(key, (Integer) defaultValue);
        } else if ("Boolean".equals(type)) {
            return mSharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if ("Float".equals(type)) {
            return mSharedPreferences.getFloat(key, (Float) defaultValue);
        } else if ("Long".equals(type)) {
            return mSharedPreferences.getLong(key, (Long) defaultValue);
        }
        return null;
    }
}
