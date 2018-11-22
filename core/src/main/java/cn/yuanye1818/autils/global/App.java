package cn.yuanye1818.autils.global;

import android.app.Application;

import cn.yuanye1818.autils.core.json.jsonholder.JsonHolder;
import cn.yuanye1818.autils.core.log.Logs;

/*************************************************
 *
 * 基础的application，创建工程时要继承此app
 *
 *************************************************/

public abstract class App extends Application implements Thread.UncaughtExceptionHandler {

    //保存自己的实例
    public static App APP;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        //添加全局的异常捕获
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logs.w(e);
        onGlobalException(t, e);
    }

    //处理全局异常
    protected abstract void onGlobalException(Thread t, Throwable e);

    public abstract String defaultLogTag();

    public abstract boolean isLog();

    public abstract JsonHolder createJsonHolder();

    public abstract String getBaseUrl();
}