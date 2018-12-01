package cn.yuanye1818.autils.core.hero;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import cn.yuanye1818.autils.core.net.NetBackI;
import cn.yuanye1818.autils.core.permission.PermissionHelper;

public interface Hero extends NetBackI, View.OnClickListener, PermissionHelper {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
