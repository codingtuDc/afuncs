package cn.yuanye1818.autils.core.net;

public interface Api {
    Api m(String m);

    Api group(String group);

    Api retrofit(RetrofitManager.RetrofitCreater retrofitCreater);

    Api okHttp(RetrofitManager.OkHttpClientCreater okHttpClientCreater);

    void main(NetBackI helper);

    void main(NetBackI helper, boolean isForceNewThread);

    void io(NetBackI helper);

    void io(NetBackI helper, boolean isForceNewThread);
}
