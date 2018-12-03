package cn.yuanye1818.autils.test;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import cn.yuanye1818.autils.MainActivity;
import cn.yuanye1818.autils.R;
import cn.yuanye1818.autils.User;
import cn.yuanye1818.autils.core.hero.Hero;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.permission.PermissionFunc;
import cn.yuanye1818.autils.core.utils.ToastFunc;
import global.RequestCode;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class MainActivity_Hero implements Hero {


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void accept(String code, Result<ResponseBody> result) {

    }

    @Override
    public Activity getAct() {
        return null;
    }

    @Override
    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {

    }
}
