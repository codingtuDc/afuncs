package global;

import com.bbk.appstore.R;

import cn.yuanye1818.func4a.core.json.jsonholder.JsonHolder;
import cn.yuanye1818.func4a.core.net.RetrofitManager;
import cn.yuanye1818.func4a.global.CoreConfigs;

public class Configs extends CoreConfigs {
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

    @Override
    public int getDefaultIcon() {
        return R.mipmap.ic_launcher;
    }

    @Override
    public String getPicDirName() {
        return "AUtils";
    }
}
