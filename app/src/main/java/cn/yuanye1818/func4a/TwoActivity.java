package cn.yuanye1818.func4a;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.yuanye1818.func4a.core.activity.CoreActivity;
import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import global.Name4RequestCode;


@Launcher(requestCode = Name4RequestCode.TWO)
public class TwoActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
