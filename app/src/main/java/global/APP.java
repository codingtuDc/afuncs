package global;

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
}
