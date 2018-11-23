package cn.yuanye1818.autils;

import android.os.Bundle;

import cn.yuanye1818.autils.core.activity.CoreActivity;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.utils.MD5;
import cn.yuanye1818.autils.net.Net;
import cn.yuanye1818.autils.net_utils_annotation.BackType;
import cn.yuanye1818.autils.net_utils_annotation.NetBack;
import cn.yuanye1818.autils.test.BeanBack;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Net.selfDetailTwo("2005").main(net);

        String xxx = MD5.md5("XXX");
        Logs.line(xxx);

    }

    @NetBack(BeanBack.class)
    public void selfDetailTwo(String messsage, @BackType User user) {
        Logs.line(user.toString());
    }

}
