package cn.yuanye1818.autils.core.net;

public interface Api {
    Api m(String m);

    void main(NetBackI helper);

    void main(NetBackI helper, boolean isForceNewThread);

    void io(NetBackI helper);

    void io(NetBackI helper, boolean isForceNewThread);
}
