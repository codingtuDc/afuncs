package cn.yuanye1818.func4a.core.res;

import cn.yuanye1818.func4a.global.App;

public class Dimens {

    public static float getDimen(int dimenId) {
        return App.APP.getResources().getDimension(dimenId);
    }

}
