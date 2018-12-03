package cn.yuanye1818.autils.core.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Field;

import cn.yuanye1818.autils.global.App;

/**
 * Created by lx on 2017/10/18.
 */

public class MobileFunc {

    /****************************************************************
     *
     * 获取屏幕宽度
     *
     ****************************************************************/

    private static int screenWidth = -1;
    /****************************************************************
     *
     * 获取屏幕高度
     *
     ****************************************************************/

    private static int screenHight = -1;
    /****************************************************************
     *
     * 获取屏幕密度
     *
     ****************************************************************/

    private static float density = -1;

    /****************************************************************
     *
     * 获取屏幕密度
     *
     ****************************************************************/
    private static int statusBarHeight = -1;

    public static int getScreenWidth() {
        if (screenWidth < 0) {
            screenWidth = getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHight() {
        if (screenHight < 0) {
            screenHight = getDisplayMetrics().heightPixels;
        }
        return screenHight;
    }


    public static float getDensity() {
        if (density < 0) {
            density = getDisplayMetrics().density;
        }
        return density;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metric = new DisplayMetrics();
        SystemFunc.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public static int getStatusBarHeight() {

        if (statusBarHeight < 0) {

            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = App.APP.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                statusBarHeight = 0;
            }
        }

        return statusBarHeight;
    }

    public static int dpToPx(int dp) {
        return (int) (getDensity() * dp);
    }

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                                               context.getResources().getDisplayMetrics());
    }
}
