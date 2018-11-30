package cn.yuanye1818.autils.core.ls.each;

public interface MapFilterEach<K, T> {
    public boolean each(int position, boolean isLast, K k, T t);
}
