package cn.yuanye1818.func4a.core.compiler;

import com.squareup.javapoet.ClassName;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import cn.yuanye1818.func4a.core.compiler.utils.Utils;

import static com.squareup.javapoet.ClassName.*;

public abstract class CoreMaker extends AbstractProcessor {

    public static final String PACKAGE_BASE = "cn.yuanye1818.func4a";
    public static final String NET_PACKAGE = PACKAGE_BASE + ".core.net";


    /**************************************************
     *
     * 不变的
     *
     **************************************************/
    //Activity
    public static final String CLASS_FRAGMENT = "android.support.v4.app.Fragment";
    public static final ClassName fragmentClass = bestGuess(CLASS_FRAGMENT);
    //Activity
    public static final String CLASS_ACTIVITY = "android.app.Activity";
    public static final ClassName activityClass = bestGuess(CLASS_ACTIVITY);
    //View
    public static final String CLASS_VIEW = "android.view.View";
    public static final ClassName viewClass = bestGuess(CLASS_VIEW);
    //rxjava2.Result
    public static final String CLASS_RESULT = "retrofit2.adapter.rxjava2.Result";
    public static final ClassName resultClass = bestGuess(CLASS_RESULT);
    //okhttp3.ResponseBody
    public static final String CLASS_RESPONSE_BODY = "okhttp3.ResponseBody";
    public static final ClassName responseBodyClass = bestGuess(CLASS_RESPONSE_BODY);
    //retrofit2.Response
    public static final String CLASS_RESPONSE = "retrofit2.Response";
    public static final ClassName responseClass = bestGuess(CLASS_RESPONSE);
    //Intent
    public static final String CLASS_INTENT = "android.content.Intent";
    public static final ClassName intentClass = bestGuess(CLASS_INTENT);
    //Flowable
    public static final String CLASS_FLOWABLE = "io.reactivex.Flowable";
    //Retrofit
    public static final String CLASS_RETROFIT = "retrofit2.Retrofit";

    /**************************************************
     *
     * 可能变的
     *
     **************************************************/
    //PermissionChecker
    public static final String CLASS_PERMISSONCHECKER = PACKAGE_BASE + ".core.permission.PermissionChecker";
    public static final ClassName permissionCheckerClass = bestGuess(CLASS_PERMISSONCHECKER);
    //Hero
    public static final String CLASS_HERO = PACKAGE_BASE + ".core.hero.Hero";
    public static final ClassName heroClass = bestGuess(CLASS_HERO);
    //PermissionHelper
    public static final String CLASS_PERMISSION_HELPER = PACKAGE_BASE + ".core.permission.PermissionHelper";
    public static final ClassName permissionHelperClass = bestGuess(CLASS_PERMISSION_HELPER);
    //Logs
    public static final String CLASS_LOGS = PACKAGE_BASE + ".core.log.Logs";
    public static final ClassName logsClass = bestGuess(CLASS_LOGS);
    //ToastFunc
    public static final String CLASS_TOAST_FUNC = PACKAGE_BASE + ".core.utils.ToastFunc";
    public static final ClassName toastFuncClass = bestGuess(CLASS_TOAST_FUNC);
    //PermissionFunc
    public static final String CLASS_PERMISSION_FUNC = PACKAGE_BASE + ".core.permission.PermissionFunc";
    public static final ClassName permissionFuncClass = bestGuess(CLASS_PERMISSION_FUNC);
    //PermissionFunc
    public static final String CLASS_ACT_FUNC = PACKAGE_BASE + ".core.utils.ActFunc";
    public static final ClassName actFuncClass = bestGuess(CLASS_ACT_FUNC);
    //Pass
    public static final String CLASS_PASS = PACKAGE_BASE + ".core.Pass";
    public static final ClassName passClass = ClassName.bestGuess(CLASS_PASS);
    //Pass
    public static final String CLASS_JSON_FUNC = PACKAGE_BASE + ".core.json.JsonFunc";
    public static final ClassName jsonFuncClass = ClassName.bestGuess(CLASS_JSON_FUNC);
    //RequestCode
    public static final String CLASS_REQUEST_CODE = PACKAGE_BASE + ".core.RequestCode";
    public static final ClassName requestCodeClass = ClassName.bestGuess(CLASS_REQUEST_CODE);
    //API
    public static final String CLASS_API = PACKAGE_BASE + ".core.net.API";
    //Api
    public static final String CLASS_ANNOTATION_API = PACKAGE_BASE + ".core.compiler.annotation.net.Api";
    //ClickView
    public static final String CLASS_CLICK_VIEW = PACKAGE_BASE + ".core.compiler.annotation.onclick.ClickView";
    //PermissionCheck
    public static final String CLASS_PERMISSION_CHECK = PACKAGE_BASE + ".core.compiler.annotation.permission.PermissionCheck";
    //PermissionCheck
    public static final String CLASS_LAUNCHER = PACKAGE_BASE + ".core.compiler.annotation.activity.Launcher";
    //NetUtils
    public static final String CLASS_NET_UTILS = PACKAGE_BASE + ".core.net.NetUtils";
    //ActLuncher
    public static final String CLASS_ACT_LUNCHER = PACKAGE_BASE + ".core.ActLuncher";



    /**************************************************
     *
     *
     *
     **************************************************/

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

}
