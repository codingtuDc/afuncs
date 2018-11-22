package cn.yuanye1818.autils.core.res;

import android.util.TypedValue;

import cn.yuanye1818.autils.global.App;

public class Dimens {

    public static float getDimen(int dimenId) {
        return App.APP.getResources().getDimension(dimenId);
    }

}
