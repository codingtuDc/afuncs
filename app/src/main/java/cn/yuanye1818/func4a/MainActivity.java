package cn.yuanye1818.func4a;

import android.Manifest;
import android.os.Bundle;

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

@Launcher(paramClasses = {User.class,
        long.class,
        String.class,
        float.class}, paramNames = {"user", "code", "name", "price"})
public class MainActivity extends CoreActivity{

    @FindView(R.id.bv)
    BottomLayerView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Net.getAlbumDetail("2005").main(hero);

    }

    @NetBack
    public void getAlbumDetail(Throwable error, Response<ResponseBody> response) {
        Logs.line("isError:" + (error != null), error.getMessage());
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
