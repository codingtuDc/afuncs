package cn.yuanye1818.autils.core.utils;

import android.app.Activity;

import cn.yuanye1818.autils.core.R;

/********************************
 *
 * 跟activity有关的工具类
 *
 ********************************/

public class ActFunc {

    /********************************
     *
     * activity跳转动画
     *
     ********************************/
    private static void overridePendingTransition(Activity act, int enterAnim, int exitAnim) {
        act.overridePendingTransition(enterAnim, exitAnim);
    }

    /********************************
     *
     * 从右进入的动画
     *
     ********************************/
    public static void actRightIn(Activity act) {
        overridePendingTransition(act, R.anim.translatex100to0, R.anim.translatex0tof100);
    }

    /********************************
     *
     * 从右出去的动画
     *
     ********************************/
    public static void actRightOut(Activity act) {
        overridePendingTransition(act, R.anim.translatexf100to0, R.anim.translatex0to100);
    }
}
