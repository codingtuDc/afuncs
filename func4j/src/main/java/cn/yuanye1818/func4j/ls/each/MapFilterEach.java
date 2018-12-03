package cn.yuanye1818.func4j.ls.each;

public interface MapFilterEach<K, T> {
    public boolean each(int position, boolean isLast, K k, T t);
}
