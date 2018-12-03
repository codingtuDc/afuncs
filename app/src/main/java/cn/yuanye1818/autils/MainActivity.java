package cn.yuanye1818.autils;

import android.os.Bundle;

import cn.yuanye1818.autils.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.autils.compiler.annotation.view.FindView;
import cn.yuanye1818.autils.core.activity.CoreActivity;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.view.layerview.BottomLayerView;
import cn.yuanye1818.autils.core.view.layerview.CenterLayerView;

public class MainActivity extends CoreActivity {

    @FindView(R.id.bv)
    BottomLayerView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @ClickView(R.id.tv)
    public void clickBt() {
    }


}
