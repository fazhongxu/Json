package xxl.com.json.permission;

import android.app.Activity;
import androidx.core.app.ActivityCompat;

import java.util.List;

/**
 * Created by xxl on 2017/11/21.
 */

public class PermissionHelper {
    private Object mObject;//Activity或者fragment
    private int mRequestCode;//申请权限时的请求码
    private String[] mPermissions;//申请的权限

    private PermissionHelper(Object object) {
        this.mObject = object;
    }

    public static void requestPermission(Object object, int permissionRequestCode, String[] permissions) {
        PermissionHelper.with(object).requestCode(permissionRequestCode).addPermissions(permissions).request();
    }

    /**
     * activity调用
     */
    public static PermissionHelper with(Activity activity) {
        return new PermissionHelper(activity);
    }

    /**
     * fragment里面调用
     */
    public static PermissionHelper with(Object object) {
        return new PermissionHelper(object);
    }

    /**
     * 权限请求码
     */
    public PermissionHelper requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 添加请求的权限
     */
    public PermissionHelper addPermissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    /**
     * 执行请求
     */
    public void request() {
        //1.判断是否是6.0以上
        if (!PermissionUtils.isOverMarshmallow()) {
            //2.如果不是6.0以上，直接反射+注解执行方法
            PermissionUtils.excuteSuccessMethod(mObject, mRequestCode);
            return;
        }
        //3.如果是6.0以上，先判断权限是否已经申请过 返回没有申请过的权限
        List<String> denyPermissions = PermissionUtils.getDenyPermissions(mObject, mPermissions);
        if (denyPermissions.size() == 0) {
            //4.如果已经申请过，直接反射+注解执行方法
            PermissionUtils.excuteSuccessMethod(mObject,mRequestCode);
        } else {
            //5.说明有权限没有申请，需要申请权限
            ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject),
                    denyPermissions.toArray(new String[denyPermissions.size()]),mRequestCode);
            //申请结果会回调 onRequestPermissionsResult 方法
            //在onRequestPermissionsResult 调用requestPermissionResult方法即可
        }
    }

    /**
     * 处理申请权限回调
     * @param object
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissionResult(Object object, int requestCode, String[] permissions) {
        //再次获取没有授予的权限
        List<String> denyPermissions = PermissionUtils.getDenyPermissions(object, permissions);

        if (denyPermissions.size() ==0) {
            //权限都授予了
            PermissionUtils.excuteSuccessMethod(object,requestCode);
        }else {
            //否则 用户拒绝了申请的权限，执行申请失败的方法
            PermissionUtils.excuteFailureMethod(object,requestCode);
        }
    }
}
