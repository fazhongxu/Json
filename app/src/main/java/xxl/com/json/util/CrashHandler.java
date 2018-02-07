package xxl.com.json.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by xxl on 2018/2/7.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static volatile CrashHandler sCrashHandler = null;
    private static final String TAG = "CrashHandler";
    /**
     * 系统默认的异常捕获
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private Context mContext;
    private Map<String, String> mInfo = new HashMap<>();
    private DateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (sCrashHandler == null) {
            synchronized (CrashHandler.class) {
                if (sCrashHandler == null) {
                    sCrashHandler = new CrashHandler();
                }
            }
        }
        return sCrashHandler;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        //获取系统默认处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置异常捕获处理为自己的异常捕获处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //处理异常情况
        Log.e("aaa", "uncaughtException: " + e.getMessage() + "--" + e.getCause());
        if (!handleException(e) && mDefaultHandler != null) {
            //用户没处理的情况下，调用系统异常处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            //重启应用 退出应用等等
            e.printStackTrace();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义异常处理
     * 做一些异常信息日志收集操作
     *
     * @return
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        //1 提示用户发生异常
        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Toast.makeText(mContext, "发生了一些小故障，应用正在重启...", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //2 设备信息收集
        deviceInfoCollect();
        //3 保存异常日志
        try {
            crashInfoFileSave(throwable);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 设备信息收集
     */
    public void deviceInfoCollect() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                mInfo.put("versionName", versionName);
                mInfo.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfo.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 异常信息保存
     */
    private String crashInfoFileSave(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : mInfo.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            String fileName = writeFile(sb.toString());
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            sb.append("an error occured while writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }

    private String writeFile(String sb) throws Exception {
        String time = mFormat.format(new Date());
        String fileName = "crash-" + time + ".log";
        String path = getGlobalPath();
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        FileOutputStream fos = new FileOutputStream(path + fileName, true);
        fos.write(sb.getBytes());
        fos.flush();
        fos.close();
        return fileName;
    }

    public static String getGlobalPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash" + File.separator;
    }

}
