package cn.yuanye1818.func4a.core.activity;

import cn.yuanye1818.func4a.core.hero.OnActivityBack;
import cn.yuanye1818.func4a.core.permission.PermissionHelper;

public interface ActivityFunc {

    public void addOnActivityBack(OnActivityBack back);

    public void addPermissionHelper(PermissionHelper helper);

    public void addToKeyDowns(WhenKeyDown keyDown);

}
