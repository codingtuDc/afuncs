package global;

import java.util.List;

import cn.yuanye1818.autils.core.json.jsonholder.JsonHolder;
import cn.yuanye1818.autils.global.App;

public class APP extends App {

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


}
