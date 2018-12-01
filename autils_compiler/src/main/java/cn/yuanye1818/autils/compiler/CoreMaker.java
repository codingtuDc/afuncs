package cn.yuanye1818.autils.compiler;

import com.squareup.javapoet.ClassName;

import java.util.Collection;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.rmi.CORBA.Util;

import cn.yuanye1818.autils.compiler.utils.Utils;

public abstract class CoreMaker extends AbstractProcessor {

    public static final String CLASS_PERMISSONCHECKER = "cn.yuanye1818.autils.core.permission.PermissionChecker";
    public static final ClassName permissionCheckerClass = ClassName
            .bestGuess(CLASS_PERMISSONCHECKER);
    //
    public static final String CLASS_ACTIVITY = "android.app.Activity";
    public static final ClassName activityClass = ClassName.bestGuess(CLASS_ACTIVITY);
    //
    public static final String CLASS_HERO = "cn.yuanye1818.autils.core.hero.Hero";
    public static final ClassName heroClass = ClassName.bestGuess(CLASS_HERO);
    //
    public static final String CLASS_VIEW = "android.view.View";
    public static final ClassName viewClass = ClassName.bestGuess(CLASS_VIEW);
    //
    public static final String CLASS_RESULT = "retrofit2.adapter.rxjava2.Result";
    public static final ClassName resultClass = ClassName.bestGuess(CLASS_RESULT);
    //
    public static final String CLASS_RESPONSE_BODY = "okhttp3.ResponseBody";
    public static final ClassName responseBodyClass = ClassName.bestGuess(CLASS_RESPONSE_BODY);
    //
    public static final String CLASS_LOGS = "cn.yuanye1818.autils.core.log.Logs";
    public static final ClassName logsClass = ClassName.bestGuess(CLASS_LOGS);
    //
    public static final String CLASS_TOAST_FUNC = "cn.yuanye1818.autils.core.utils.ToastFunc";
    public static final ClassName toastFuncClass = ClassName.bestGuess(CLASS_TOAST_FUNC);
    //
    public static final String CLASS_RESPONSE = "retrofit2.Response";
    public static final ClassName responseClass = ClassName.bestGuess(CLASS_RESPONSE);
    //
    public static final String CLASS_PERMISSION_UTILS = "cn.yuanye1818.autils.core.permission.PermissionUtils";
    public static final ClassName permissionUtilsClass = ClassName
            .bestGuess(CLASS_PERMISSION_UTILS);
    //
    public static final String CLASS_PREFERENCES = "cn.yuanye1818.autils.global.Preferences";
    public static final ClassName preferencesClass = ClassName.bestGuess(CLASS_PREFERENCES);
    //
    public static final String CLASS_INTENT = "android.content.Intent";
    public static final ClassName intentClass = ClassName.bestGuess(CLASS_INTENT);
    //
    public static final String NET_PACKAGE = "cn.yuanye1818.autils.core.net";
    public static final String CLASS_API = "cn.yuanye1818.autils.core.net.Api";
    public static final String CLASS_NET_UTILS = "cn.yuanye1818.autils.core.net.NetUtils";
    public static final String CLASS_FLOWABLE = "io.reactivex.Flowable";
    public static final String CLASS_RETROFIT = "retrofit2.Retrofit";


    protected Elements elementUtils;
    protected Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        Utils.setTypeUtils(typeUtils);
        Utils.setProcessingEnv(processingEnv);
    }

    protected boolean isNull(Collection c) {
        return c == null || c.size() <= 0;
    }

}
