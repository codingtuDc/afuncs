package cn.yuanye1818.func4a.test;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.yuanye1818.func4a.MainActivity;
import cn.yuanye1818.func4a.core.hero.Hero;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class MainActivity_Hero implements Hero {

    private MainActivity binder;

    public MainActivity_Hero(MainActivity binder, View view) {
        this.binder = binder;
    }

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
        Object binder = this.binder;
        if (binder instanceof Activity) {
            return (Activity) binder;
        } else if (binder instanceof Fragment) {
            return ((Fragment) binder).getActivity();
        }
        return null;
    }

    @Override
    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {

    }
}
