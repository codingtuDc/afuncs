package cn.yuanye1818.func4a;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.yuanye1818.func4a.core.Pass;
import cn.yuanye1818.func4a.core.activity.CoreActivity;
import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;


@Launcher(paramClasses = User.class, paramNames = "user")
public class ThreeActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        User user = hero.i().user();
    }
}
