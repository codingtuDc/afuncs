package cn.yuanye1818.autils.test;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;

import cn.yuanye1818.autils.compiler.annotation.permission.PermissionCheck;
import cn.yuanye1818.autils.core.hero.Hero;
import cn.yuanye1818.autils.core.permission.PermissionHelper;
import cn.yuanye1818.autils.core.permission.PermissionUtils;
import cn.yuanye1818.autils.core.utils.StringFunc;
import cn.yuanye1818.autils.global.Preferences;

public class PermissonChecker {

    public static final int CHECK_STORE_FILE = 0;

    public static final String CACHE_CHECK_STORE_FILE = "permission_check_store_file";

    public static void checkStoreFile(Hero hero) {
        check(hero, CACHE_CHECK_STORE_FILE, CHECK_STORE_FILE,
              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    private static void check(Hero hero, int code, String[] ps) {
        Activity act = hero.getAct();
        if (act != null) {
            if (PermissionUtils.check(act, ps)) {
                hero.onPermissionsBack(code, ps, new int[ps.length]);
            }
        }
    }

    private static void check(Hero hero, String name, int code, String[] ps) {
        if (!Preferences.getPermissionChecked(name)) {
            Preferences.putPermissionChecked(name);
            check(hero, code, ps);
        } else {
            hero.onPermissionsBack(code, null, null);
        }
    }


}
