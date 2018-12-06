package cn.yuanye1818.func4a.core.hero;

import android.view.View;

import cn.yuanye1818.func4a.core.activity.IntentsI;
import cn.yuanye1818.func4a.core.net.NetBackI;
import cn.yuanye1818.func4a.core.permission.PermissionHelper;

public interface Hero
        extends NetBackI, View.OnClickListener, PermissionHelper, OnActivityBack {
}
