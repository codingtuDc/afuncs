package cn.yuanye1818.autils.global;

import cn.yuanye1818.autils.core.json.jsonholder.JsonHolder;
import cn.yuanye1818.autils.core.net.RetrofitManager;

public abstract class AutilsConfigs {

    public static AutilsConfigs CONFIGS;

    public static AutilsConfigs configs() {
        if (CONFIGS == null) {
            CONFIGS = App.APP.createConfigs();
        }
        return CONFIGS;
    }


    //处理全局异常
    protected abstract void onGlobalException(Thread t, Throwable e);

    public abstract String defaultLogTag();

    public abstract boolean isLog();

    public abstract JsonHolder createJsonHolder();

    public abstract String getBaseUrl();

    public abstract RetrofitManager.OkHttpClientCreater getDefaultOkHttpClientCreater();

    public abstract RetrofitManager.RetrofitCreater getDefaultRetrofitCreater();
}
