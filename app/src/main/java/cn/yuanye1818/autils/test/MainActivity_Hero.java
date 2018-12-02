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

    private MainActivity binder;

    public MainActivity_Hero(MainActivity binder) {
        this.binder = binder;
        setOnclickListener(R.id.ll);
        setOnclickListener(R.id.ll);
        setOnclickListener(R.id.ll);
    }

    private void setOnclickListener(int id) {
        try {
            this.binder.findViewById(id).setOnClickListener(this);
        } catch (Exception e) {
            Logs.w(e);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == 123) {
            binder.clickLl();
        } else if (id == 12356) {
            binder.clickAvatar(v, (int) v.getTag(R.id.tag_position), (User) v.getTag(R.id.tag_obj));
        }
    }

    @Override
    public void accept(String code, Result<ResponseBody> result) {
        if ("getAlbumDetail".equals(code)) {
            new BeanBack<User>() {
                @Override
                public void back(String message, User user) {
                    binder.getAlbumDetail(message, user);
                }
            }.accept(code, result);
        }
    }

    ///

    @Override
    public Activity getAct() {
        return null;
    }

    @Override
    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PermissonChecker.CHECK_STORE_FILE) {
            try {
                binder.storeFile();
            } catch (Exception e) {
                Logs.w(e);
            }
        }

        if (requestCode == PermissonChecker.CHECK_STORE_FILE) {
            if (PermissionFunc.allow(grantResults)) {
                binder.storeFile();
            } else {
                ToastFunc.toast("您禁用了相关权限，无法完成操作");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.STORY) {
            binder.storyBack(data);
        }
    }
}
