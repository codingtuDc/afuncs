package cn.yuanye1818.autils.core.log;

import android.util.Log;

import java.util.Collection;
import java.util.Iterator;

import cn.yuanye1818.autils.core.utils.StringFunc;
import cn.yuanye1818.autils.global.App;

public class Logs {

    //封装了log方法，可以打印多种类型

    public static final int LEVEL_INFO = 0;
    public static final int LEVEL_WARNING = 1;
    public static final int LEVEL_ERROR = 2;

    public static void i(Object msg) {
        log(LEVEL_INFO, App.APP.defaultLogTag(), msg);
    }

    public static void e(Object msg) {
        log(LEVEL_ERROR, App.APP.defaultLogTag(), msg);
    }

    public static void w(Object msg) {
        log(LEVEL_WARNING, App.APP.defaultLogTag(), msg);
    }

    public static void i(String tag, Object msg) {
        log(LEVEL_INFO, tag, msg);
    }

    public static void e(String tag, Object msg) {
        log(LEVEL_ERROR, tag, msg);
    }

    public static void w(String tag, Object msg) {
        log(LEVEL_WARNING, tag, msg);
    }

    private static void log(int level, String tag, Object msg) {
        if (App.APP.isLog() && StringFunc.isNotBlank(tag)) {
            if (msg == null) {
                logNull(level, tag);
            } else if (msg instanceof String) {
                logString(level, tag, (String) msg);
            } else if (msg instanceof Throwable) {
                logThrowable(level, tag, (Throwable) msg);
            } else if (msg instanceof Collection) {
                logCollection(level, tag, (Collection) msg);
            } else if (msg.getClass().isArray()) {
                logArray(level, tag, (Object[]) msg);
            } else {
                logOther(level, tag, msg);
            }
        }
    }

    private static void logOther(int level, String tag, Object msg) {
        baseLog(level, tag, msg.toString());
    }

    private static void logNull(int level, String tag) {
        baseLog(level, tag, "this is null");
    }

    private static void logString(int level, String tag, String msg) {
        baseLog(level, tag, msg);
    }

    private static void logThrowable(int level, String tag, Throwable t) {
        baseLog(level, tag, " ");
        baseLog(level, tag, "=Throwable================================================");
        baseLog(level, tag, "----------------------------------------------------------");
        baseLog(level, tag, t);
        baseLog(level, tag, "----------------------------------------------------------");
        baseLog(level, tag, "==========================================================");
        baseLog(level, tag, " ");
    }

    private static void logCollection(int level, String tag, Collection cs) {
        baseLog(level, tag, " ");
        baseLog(level, tag, "=Collection===============================================");
        baseLog(level, tag, "----------------------------------------------------------");
        if (cs.size() <= 0) {
            baseLog(level, tag, "Collection is empty");
        } else {
            Iterator iterator = cs.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Object next = iterator.next();
                baseLog(level, tag, "index:" + index + " | " + next);
                index++;
            }
        }
        baseLog(level, tag, "----------------------------------------------------------");
        baseLog(level, tag, "==========================================================");
        baseLog(level, tag, " ");
    }

    private static void logArray(int level, String tag, Object[] objs) {
        baseLog(level, tag, " ");
        baseLog(level, tag, "=Array====================================================");
        baseLog(level, tag, "----------------------------------------------------------");
        if (objs.length <= 0) {
            baseLog(level, tag, "Array is empty");
        } else {
            for (int i = 0; i < objs.length; i++) {
                baseLog(level, tag, "index:" + i + " | " + objs[i]);
            }
        }
        baseLog(level, tag, "----------------------------------------------------------");
        baseLog(level, tag, "==========================================================");
        baseLog(level, tag, " ");
    }

    private static void baseLog(int level, String tag, String msg) {
        switch (level) {
            case LEVEL_INFO:
                Log.i(tag, msg);
                break;
            case LEVEL_WARNING:
                Log.w(tag, msg);
                break;
            case LEVEL_ERROR:
                Log.e(tag, msg);
                break;
        }
    }

    private static void baseLog(int level, String tag, Throwable msg) {
        switch (level) {
            case LEVEL_INFO:
                Log.i(tag, msg.getMessage(), msg);
                break;
            case LEVEL_WARNING:
                Log.w(tag, msg.getMessage(), msg);
                break;
            case LEVEL_ERROR:
                Log.e(tag, msg.getMessage(), msg);
                break;
        }
    }

}
