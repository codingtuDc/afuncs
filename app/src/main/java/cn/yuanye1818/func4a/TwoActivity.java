package cn.yuanye1818.func4a;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.yuanye1818.func4a.core.ActLuncher;
import cn.yuanye1818.func4a.core.activity.CoreActivity;
import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import cn.yuanye1818.func4a.core.compiler.annotation.net.NetBack;
import cn.yuanye1818.func4a.core.net.NetBackI;
import cn.yuanye1818.func4a.core.net.NetUtils;
import cn.yuanye1818.func4a.test.Net;
import okhttp3.ResponseBody;
import retrofit2.Response;


@Launcher
public class TwoActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Net.getAlbumDetail("").main(hero);
    }

    @NetBack
    public void getAlbumDetail(Throwable error, Response<ResponseBody> response) {

    }
}
