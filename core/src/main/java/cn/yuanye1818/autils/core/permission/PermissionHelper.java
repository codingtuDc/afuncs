package cn.yuanye1818.autils.core.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.io.File;

public interface PermissionHelper {

    public Activity getAct();

    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults);

}
