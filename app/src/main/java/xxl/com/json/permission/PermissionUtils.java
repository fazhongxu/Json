package xxl.com.json.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxl on 2017/11/21.
 */

public class PermissionUtils {
    private PermissionUtils() {

    }

    /**
     * 判断是否是6.0以上
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 反射+注解   执行带有PermissionSuccess注解的方法
     *
     * @param object      Activity or Fragmetn
     * @param requestCode 权限请求码
     */
    public static void excuteSuccessMethod(Object object, int requestCode) {
        //反射获取类的所有方法
        Method[] methods = object.getClass().getDeclaredMethods();
        //遍历方法
        for (Method method : methods) {
            //获取方法上的注解 获取到带有PermissionSuccess注解的方法
            PermissionSuccess permissionSuccess = method.getAnnotation(PermissionSuccess.class);
            if (permissionSuccess != null) {
                //获取注解携带的参数 和requestCode做比较，如果是自己请求的权限，则反射执行方法
                if (requestCode == permissionSuccess.requstCode()) {
                    //方法.invoke 执行方法
                    try {
                        method.setAccessible(true);//设置私有也可以访问
                        //参数1 类的实例 参数2 相应方法的参数 如callPhone(); 这里callPhone()没有参数，所以传Object空数组就行
                        method.invoke(object, new Object[]{});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * 执行权限申请授予失败的方法
     * @param object
     * @param requestCode
     */
    public static void excuteFailureMethod(Object object, int requestCode) {
        //反射获取类的所有方法
        Method[] methods = object.getClass().getDeclaredMethods();
        //遍历方法
        for (Method method : methods) {
            //获取方法上的注解 获取到带有permissionFailure注解的方法
            PermissionFailure permissionFailure = method.getAnnotation(PermissionFailure.class);
            if (permissionFailure != null) {
                //获取注解携带的参数 和requestCode做比较，如果是自己请求的权限，则反射执行方法
                if (requestCode == permissionFailure.requstCode()) {
                    //方法.invoke 执行方法
                    try {
                        method.setAccessible(true);//设置私有也可以访问
                        //参数1 类的实例 参数2 相应方法的参数 如callPhone(); 这里callPhone()没有参数，所以传Object空数组就行
                        method.invoke(object, new Object[]{});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取拒绝的权限
     *
     * @param object
     * @param permissions
     * @return
     */
    public static List<String> getDenyPermissions(Object object, String[] permissions) {
        Activity activity = getActivity(object);
        List<String> list = new ArrayList<>();
        //遍历权限，检查，把未通过的权限添加到集合返回
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                list.add(permission);
            }
        }
        return list;
    }

    /**
     * 获取activity
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        }
        return null;
    }

}
