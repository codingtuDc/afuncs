package cn.yuanye1818.autils.core.hero;

import java.lang.reflect.Constructor;

import cn.yuanye1818.autils.core.log.Logs;

public class HeroFunc {

    public static Hero bind(Object binder) {
        try {
            String helperName = binder.getClass().getName() + "_Hero";
            Constructor<Hero> constructor = (Constructor<Hero>) Class.forName(helperName)
                                                                     .getConstructor(
                                                                             binder.getClass());
            return constructor.newInstance(binder);
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

}
