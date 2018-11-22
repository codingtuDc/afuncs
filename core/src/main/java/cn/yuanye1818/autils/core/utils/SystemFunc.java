package cn.yuanye1818.autils.core.utils;

import android.content.Context;
import android.media.AudioManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import cn.yuanye1818.autils.global.App;

/**
 * 获取系统的服务
 */

public class SystemFunc {

    private static Context getContext() {
        return App.APP;
    }

    public static WindowManager getWindowManager() {
        return (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public static InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static AudioManager getAudioManager() {
        return (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
    }
}
