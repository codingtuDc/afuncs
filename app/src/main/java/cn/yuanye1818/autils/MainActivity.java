package cn.yuanye1818.autils;

import android.os.Bundle;

import com.yuanye1818.autils.net.Net;

import cn.yuanye1818.autils.core.CoreActivity;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.net.NetHelpers;
import cn.yuanye1818.autils.net_utils_annotation.NetBack;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetHelpers.bind(this);
        Net.getAlbumDetail("2005").run(this);
    }

    @NetBack(code = Net.GET_ALBUM_DETAIL)
    public void userBack(int code, String msg, User user) {
        Logs.i("userBack");
    }

}
