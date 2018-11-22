package cn.yuanye1818.autils.core.net;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import cn.yuanye1818.autils.core.log.Logs;

public class NetHelpers {

    private static HashMap<Integer, NetHelper> helpers;

    public static void bind(Activity act) {
        try {
            String helperName = act.getClass().getName() + "_Helper";
            Constructor<NetHelper> constructor = (Constructor<NetHelper>) Class.forName(helperName)
                                                                               .getConstructor(
                                                                                 act.getClass());
            NetHelper helper = constructor.newInstance(act);
            if (helpers == null) {
                helpers = new HashMap<Integer, NetHelper>();
            }
            helpers.put(act.hashCode(), helper);
        } catch (Exception e) {
            Logs.w(e);
        }
    }

    public static NetHelper getHelper(int hashCode) {
        if (helpers != null) {
            return helpers.get(hashCode);
        }
        return null;
    }
}
