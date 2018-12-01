package cn.yuanye1818.autils.core.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import cn.yuanye1818.autils.global.App;

public class PermissionUtils {

    private static final int CHECK_PERMISSION = 0;

    public static boolean check(Activity act, String... permissioins) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!allow(permissioins)) {
                act.requestPermissions(permissioins, CHECK_PERMISSION);
                return false;
            }
        }
        return true;
    }

    public static boolean allow(String[] permissions) {
        for (String permission : permissions) {
            if (!allow(permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean allow(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return App.APP.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static boolean allow(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
