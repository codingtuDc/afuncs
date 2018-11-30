package cn.yuanye1818.autils.core.ls.filter;

public interface MapFilter<K, T> {
    public boolean filter(K k, T t);
}
