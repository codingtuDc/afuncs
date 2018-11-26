package global;

import cn.yuanye1818.autils.core.json.jsonholder.JsonHolder;
import cn.yuanye1818.autils.core.net.RetrofitManager;
import cn.yuanye1818.autils.global.AutilsConfigs;

public class Configs extends AutilsConfigs {
    @Override
    protected void onGlobalException(Thread t, Throwable e) {
    }

    @Override
    public String defaultLogTag() {
        return "autilsLog";
    }

    @Override
    public boolean isLog() {
        return true;
    }

    @Override
    public JsonHolder createJsonHolder() {
        return null;
    }

    @Override
    public String getBaseUrl() {
        return "https://bookgood.wismoly.com/app/api/v1/";
    }

    @Override
    public RetrofitManager.OkHttpClientCreater getDefaultOkHttpClientCreater() {
        return null;
    }

    @Override
    public RetrofitManager.RetrofitCreater getDefaultRetrofitCreater() {
        return null;
    }
}
