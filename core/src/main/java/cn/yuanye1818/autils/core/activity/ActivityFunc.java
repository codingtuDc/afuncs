package cn.yuanye1818.autils.core.activity;

import android.view.KeyEvent;

import cn.yuanye1818.autils.core.hero.OnActivityBack;
import cn.yuanye1818.autils.core.permission.PermissionHelper;

public interface ActivityFunc {

    public void addOnActivityBack(OnActivityBack back);

    public void addPermissionHelper(PermissionHelper helper);

    public void addToKeyDowns(WhenKeyDown keyDown);

}
