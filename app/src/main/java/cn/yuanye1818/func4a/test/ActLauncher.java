package cn.yuanye1818.func4a.test;

import android.app.Activity;
import android.content.Intent;

import cn.yuanye1818.func4a.MainActivity;
import cn.yuanye1818.func4a.User;
import cn.yuanye1818.func4a.core.json.JsonFunc;
import cn.yuanye1818.func4a.core.utils.ActFunc;
import global.Pass;
import global.Code4Request;

public class ActLauncher {

    public static final void mainAct(Activity act) {
        ActFunc.startActivity(act, MainActivity.class);
    }

    public static final void mainAct1(Activity act) {
        ActFunc.startActivityForResult(act, MainActivity.class, Code4Request.STORY);
    }

    public static final void mainAct2(Activity act, User user) {
        Intent intent = new Intent(act, MainActivity.class);
        intent.putExtra(Pass.USER, JsonFunc.toJson(user));
        ActFunc.startActivity(act, intent);
    }

    public static final void mainAct3(Activity act, User user,int code) {
        Intent intent = new Intent(act, MainActivity.class);
        intent.putExtra(Pass.USER, JsonFunc.toJson(user));
        intent.putExtra(Pass.CODE, code);
        ActFunc.startActivityForResult(act, intent, Code4Request.STORY);
    }

}
