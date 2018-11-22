package cn.yuanye1818.autils.core.utils;

import java.util.Collection;

public class CollectionFunc {

    public static int count(Collection cs) {
        return cs == null ? 0 : cs.size();
    }

}
