package cn.yuanye1818.func4a;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bbk.appstore.R;

import java.io.IOException;

import cn.yuanye1818.func4a.core.ActLuncher;
import cn.yuanye1818.func4a.core.activity.BaseTipActivity;
import cn.yuanye1818.func4a.core.activity.CoreActivity;
import cn.yuanye1818.func4a.core.bean.WH;
import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import cn.yuanye1818.func4a.core.compiler.annotation.net.NetBack;
import cn.yuanye1818.func4a.core.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.func4a.core.compiler.annotation.permission.PermissionCheck;
import cn.yuanye1818.func4a.core.compiler.annotation.view.FindView;
import cn.yuanye1818.func4a.core.log.Logs;
import cn.yuanye1818.func4a.core.net.BaseInterceptor;
import cn.yuanye1818.func4a.core.net.Net;
import cn.yuanye1818.func4a.core.utils.MobileFunc;
import cn.yuanye1818.func4a.core.utils.ToastFunc;
import cn.yuanye1818.func4a.core.view.layerview.BottomLayerView;
import cn.yuanye1818.func4a.global.CoreConfigs;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class MainActivity extends BaseTipActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStartBtClick(View v) {
        ActLuncher.oneActivity(getThis());
    }

    @Override
    protected int getStartBtImage() {
        return R.mipmap.bt_tip_start;
    }

    @Override
    protected int getDotUncheckImage() {
        return R.drawable.bg_tip_dot_uncheck;
    }

    @Override
    protected int getDotCheckedImage() {
        return R.drawable.bg_tip_dot_checked;
    }

    @Override
    protected int getDesignStartBtMarginBottom() {
        return 364;
    }

    @Override
    protected int getDesignDotMarginBottom() {
        return 180;
    }

    @Override
    protected int getDesignDotMarginLeft() {
        return 40;
    }

    @Override
    protected WH getDesignPicWH() {
        return new WH(1500, 2668);
    }

    @Override
    protected int getDesignDotWidth() {
        return 28;
    }

    @Override
    protected String[] getPics() {
        return new String[]{
                "bg_tips1.jpg",
                "bg_tips2.jpg",
                "bg_tips3.jpg"
        };
    }
}
