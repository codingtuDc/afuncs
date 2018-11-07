package cn.yuanye1818.autils;

import android.os.Bundle;
import android.view.View;

import cn.yuanye1818.autils.core.CoreActivity;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.utils.MarginFunc;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int l = MarginFunc.l(null);
        Logs.i(l);

    }
}
