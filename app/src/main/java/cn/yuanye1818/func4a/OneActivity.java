package cn.yuanye1818.func4a;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bbk.appstore.R;

import cn.yuanye1818.func4a.core.ActLuncher;
import cn.yuanye1818.func4a.core.activity.CoreActivity;
import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import cn.yuanye1818.func4a.core.compiler.annotation.onactivityresult.OnResult;
import cn.yuanye1818.func4a.core.compiler.annotation.view.FindView;
import cn.yuanye1818.func4a.core.image.ImageFunc;
import cn.yuanye1818.func4a.core.net.API;
import cn.yuanye1818.func4a.core.net.Net;


@Launcher
public class OneActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_one);
    }

}
