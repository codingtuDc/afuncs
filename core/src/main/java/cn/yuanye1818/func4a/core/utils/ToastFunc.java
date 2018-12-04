package cn.yuanye1818.func4a.core.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import cn.yuanye1818.func4a.core.R;
import cn.yuanye1818.func4a.global.App;

/**
 * Created by lx on 2017/10/18.
 */

public class ToastFunc {

    /****************************************************************
     *
     * toast
     *
     ****************************************************************/

    private static Toast toast;

    //系统默认的toast
    public static void toast(String text) {
        if (toast == null)
            toast = Toast.makeText(App.APP, text, Toast.LENGTH_SHORT);
        else
            toast.setText(text);
        toast.show();
    }

    //自定义中间显示的toast
    private static void makeToastInCenter(int layoutId) {
        makeToastInCenter(InflateFunc.inflate(layoutId));
    }

    //自定义中间显示的toast
    private static void makeToastInCenter(View layout) {
        Toast toast = new Toast(App.APP);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //默认的中间显示的toast
    public static void toastInCenter(Object text) {
        View layout = InflateFunc.inflate(R.layout.toast_in_center);
        ViewFunc.setText(layout, text);
        makeToastInCenter(layout);
    }

}
