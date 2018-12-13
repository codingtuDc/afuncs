package cn.yuanye1818.func4a;

import android.Manifest;
import android.os.Bundle;

import com.bbk.appstore.R;

import java.io.IOException;

import cn.yuanye1818.func4a.core.activity.CoreActivity;
import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import cn.yuanye1818.func4a.core.compiler.annotation.net.NetBack;
import cn.yuanye1818.func4a.core.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.func4a.core.compiler.annotation.permission.PermissionCheck;
import cn.yuanye1818.func4a.core.compiler.annotation.view.FindView;
import cn.yuanye1818.func4a.core.log.Logs;
import cn.yuanye1818.func4a.core.net.BaseInterceptor;
import cn.yuanye1818.func4a.core.net.Net;
import cn.yuanye1818.func4a.core.view.layerview.BottomLayerView;
import cn.yuanye1818.func4a.global.CoreConfigs;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;


//https://main.appstore.vivo.com.cn/rec/newapps?param=ZXhSxy_3xXhFZ2hDtkCqKMkmqD6uxyrfc0hHUyuF6M4CTe-0W9E1WeE2c0WD65hYc0uI6M4PTNdfR9-1TeQ2cyPnUyh2xlJFh2hDc0uwxH1nx5kgxXhsxv6ec94fJX8SZl_1cykgU0hzcv6H6519LR_1tMTYTv6H65UD65kgTz6nUe4DTD6CtM-CTN-fTNKDWy-46MKDT9KCTeQl65T9Z2EDZ0TlT0ZCJX8nUyWjRlWCcN4PJ2rYtMZYTv6fxyr4656wc2C56RJ9L5gYtMQ2ZR8fRl6ucXWSx0mgTMcfWv6fxyr4KR8Vh2hDM2rF6MCYU5PsJX8sZR_3L0h1Rl6uce42L5CuLM4mWeHlW9TfTec4T9dPTed2xXKgh4uyqq6fxyr4KR8Vh2hDtMQ2Z5J46RW4tMQ2x5gH65fgk2hHx5HVkaJwJXTgTzklK9TPTed4WN-4ONQ2LX6PRl_1cykgTQ&jvq=1.0.9

@Launcher(paramClasses = {User.class, long.class, String.class, float.class}, paramNames = {"user",
        "code",
        "name",
        "price"})
public class MainActivity extends CoreActivity {

    @FindView(R.id.bv)
    BottomLayerView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////jvq=1.0.9

        String param = "ZXhSxy_3xXhFZ2hDtkCqKMkmqD6uxyrfc0hHUyuF6M4CTe-0W9E1WeE2c0WD65hYc0uI6M4PTNdfR9-1TeQ2cyPnUyh2xlJFh2hDc0uwxH1nx5kgxXhsxv6ec94fJX8SZl_1cykgU0hzcv6H6519LR_1tMTYTv6H65UD65kgTz6nUe4DTD6CtM-CTN-fTNKDWy-46MKDT9KCTeQl65T9Z2EDZ0TlT0ZCJX8nUyWjRlWCcN4PJ2rYtMZYTv6fxyr4656wc2C56RJ9L5gYtMQ2ZR8fRl6ucXWSx0mgTMcfWv6fxyr4KR8Vh2hDM2rF6MCYU5PsJX8sZR_3L0h1Rl6uce42L5CuLM4mWeHlW9TfTec4T9dPTed2xXKgh4uyqq6fxyr4KR8Vh2hDtMQ2Z5J46RW4tMQ2x5gH65fgk2hHx5HVkaJwJXTgTzklK9TPTed4WN-4ONQ2LX6PRl_1cykgTQ";

        Net.newApps(param, "1.0.9").main(hero);

    }

    @NetBack
    public void newApps(Throwable error, Response<ResponseBody> response) {
        try {
            String json = response.body().string();
            Logs.line(json);
        } catch (IOException e) {
            Logs.w(e);
        }
    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new BaseInterceptor(CoreConfigs.configs().isLog())).build();
    }

    @PermissionCheck({Manifest.permission.CAMERA})
    public void permission() {

    }

    @ClickView(R.id.tv)
    public void clickBt() {
    }


}
