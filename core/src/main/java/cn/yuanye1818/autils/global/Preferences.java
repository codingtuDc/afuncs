package cn.yuanye1818.autils.global;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

import cn.yuanye1818.autils.core.bean.CoreBean;
import cn.yuanye1818.autils.core.json.JsonFunc;

public class Preferences {

    public static SharedPreferences sf() {
        return PreferenceManager.getDefaultSharedPreferences(App.APP);
    }

    /**************************************************
     *
     * put
     *
     **************************************************/

    private static void putString(String key, String value) {
        sf().edit().putString(key, value).commit();
    }

    private static void putInt(String key, int value) {
        sf().edit().putInt(key, value).commit();
    }

    private static void putBoolean(String key, boolean value) {
        sf().edit().putBoolean(key, value).commit();
    }

    private static void putFloat(String key, float value) {
        sf().edit().putFloat(key, value).commit();
    }

    private static void putLong(String key, long value) {
        sf().edit().putLong(key, value).commit();
    }

    private static void putStringSet(String key, Set<String> values) {
        sf().edit().putStringSet(key, values).commit();
    }

    private static void putBean(String key, CoreBean value) {
        sf().edit().putString(key, value.toString()).commit();
    }

    /**************************************************
     *
     * get
     *
     **************************************************/

    public static String getString(String key) {
        return sf().getString(key, null);
    }

    public static int getInt(String key) {
        return sf().getInt(key, 0);
    }

    public static float getFloat(String key) {
        return sf().getFloat(key, 0f);
    }

    public static boolean getBoolean(String key) {
        return sf().getBoolean(key, false);
    }

    public static long getLong(String key) {
        return sf().getLong(key, 0);
    }

    public static Set<String> getStringSet(String key) {
        return sf().getStringSet(key, null);
    }

    public static <T> T getT(Class<T> tClass, String key) {
        String json = getString(key);
        return JsonFunc.toBean(tClass, json);
    }

    /**************************************************
     *
     * 特别方法
     *
     **************************************************/

    public static void putPermissionChecker(String key) {

    }

    public static boolean getPermissionChecked(String key) {
        return getBoolean(key);
    }

    public static void putPermissionChecked(String key) {
        putBoolean(key, true);
    }
}
