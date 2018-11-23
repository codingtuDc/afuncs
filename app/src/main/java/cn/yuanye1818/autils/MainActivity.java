package cn.yuanye1818.autils;

import android.os.Bundle;

import cn.yuanye1818.autils.core.activity.CoreActivity;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.net.Net;
import cn.yuanye1818.autils.core.utils.MD5;
import cn.yuanye1818.autils.compiler.annotation.BackType;
import cn.yuanye1818.autils.compiler.annotation.NetBack;
import cn.yuanye1818.autils.test.BeanBack;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String xxx = MD5.md5("XXX");
        Logs.line(xxx);

        Net.getAlbumDetail("2005").main(net);


    }

    @NetBack
    public void getAlbumDetail(Throwable error, Response<ResponseBody> response) {

    }

    @NetBack(BeanBack.class)
    public void selfDetailTwo(String messsage, @BackType User user) {
        Logs.line(user.toString());
    }

}
