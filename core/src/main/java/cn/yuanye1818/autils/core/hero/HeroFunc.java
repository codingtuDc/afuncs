package cn.yuanye1818.autils.core.hero;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;

import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.utils.ViewFunc;

public class HeroFunc {

    public static Hero bind(Activity binder) {
        try {
            String helperName = binder.getClass().getName() + "_Hero";
            Constructor<Hero> constructor = (Constructor<Hero>) Class.forName(helperName)
                                                                     .getConstructor(
                                                                             binder.getClass(),
                                                                             View.class);
            return constructor.newInstance(binder, ViewFunc.getRootView(binder));
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

    public static Hero bind(Object binder, View view) {
        try {
            String helperName = binder.getClass().getName() + "_Hero";
            Constructor<Hero> constructor = (Constructor<Hero>) Class.forName(helperName)
                                                                     .getConstructor(
                                                                             binder.getClass(),
                                                                             View.class);
            return constructor.newInstance(binder, view);
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

}
