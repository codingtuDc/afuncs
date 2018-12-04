package cn.yuanye1818.func4a.test;

import android.Manifest;

import cn.yuanye1818.func4a.core.hero.Hero;
import cn.yuanye1818.func4a.core.permission.PermissionFunc;

public class PermissonChecker {

    public static final int CHECK_STORE_FILE = 0;

    public static final String CACHE_CHECK_STORE_FILE = "permission_check_store_file";

    public static void checkStoreFile(Hero hero) {
        PermissionFunc.check(hero, CACHE_CHECK_STORE_FILE, CHECK_STORE_FILE,
                             new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

}
