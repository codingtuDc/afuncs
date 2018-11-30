package cn.yuanye1818.autils.core.ls.each;

public interface FilterEach<T> {
    public boolean each(int position, boolean isLast, T t);
}
