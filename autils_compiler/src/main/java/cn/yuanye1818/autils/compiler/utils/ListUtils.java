package cn.yuanye1818.autils.compiler.utils;

import com.squareup.javapoet.ParameterSpec;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ListUtils {

    public interface Each<T> {
        public boolean each(int position, T t);
    }

    public static interface EachWithFilter<T> {

        public boolean each(int position, boolean isLast, T t);

        public boolean filter(T t);
    }

    public static int count(Collection cs) {
        return cs == null ? 0 : cs.size();
    }

    public static int count(Object... cs) {
        return cs == null ? 0 : cs.length;
    }

//    public static int count(int[] cs) {
//        return cs == null ? 0 : cs.length;
//    }

    public static <T> void ls(Collection<? extends T> ts, Each<T> each) {
        if (count(ts) > 0) {
            int index = 0;
            Iterator<? extends T> iterator = ts.iterator();
            while (iterator.hasNext()) {
                if (each.each(index, iterator.next())) {
                    break;
                }
                index++;
            }
        }
    }

    public static <T> void ls(Collection<? extends T> ts, EachWithFilter<T> each) {
        if (count(ts) > 0) {
            int index = 0;
            Iterator<? extends T> iterator = ts.iterator();
            T t = null;
            T next = null;
            while (iterator.hasNext()) {
                next = iterator.next();
                if (t == null) {
                    if (!each.filter(next)) {
                        t = next;
                    }
                    continue;
                }

                if (!each.filter(next)) {
                    if (each.each(index, false, t)) {
                        break;
                    }
                    index++;
                    t = next;
                }
            }
            each.each(index, true, t);
        }
    }

    public static <K, T> void ls(final Map<K, T> ts, final Each<T> each) {
        if (ts != null) {
            ls(ts.keySet(), new Each<K>() {
                @Override
                public boolean each(int position, K k) {
                    return each.each(position, ts.get(k));
                }
            });
        }
    }

    public static <T> void ls(T[] ts, Each<T> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

    public static void ls(int[] ts, Each<Integer> each) {
        for (int i = 0; i < count(ts); i++) {
            if (each.each(i, ts[i])) {
                return;
            }
        }
    }

}
