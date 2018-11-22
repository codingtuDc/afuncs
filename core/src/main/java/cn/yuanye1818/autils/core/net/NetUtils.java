package cn.yuanye1818.autils.core.net;

import java.util.HashMap;
import java.util.Map;

import cn.yuanye1818.autils.global.App;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class NetUtils {

    private static Map<String, Retrofit> retrofits;

    private static Retrofit newRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BaseInterceptor(App.APP.isLog())).build();
        return new Retrofit.Builder().client(okHttpClient)
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .baseUrl(baseUrl).build();
    }

    public static <T> T create(Class<T> tClass) {
        return create(tClass, App.APP.getBaseUrl());
    }

    public static <T> T create(Class<T> tClass, String baseUrl) {
        Retrofit retrofit = null;
        if (retrofits == null) {
            retrofits = new HashMap<String, Retrofit>();
        } else {
            retrofit = retrofits.get(baseUrl);
        }
        if (retrofit == null) {
            retrofit = newRetrofit(baseUrl);
            retrofits.put(baseUrl, retrofit);
        }
        return retrofit.create(tClass);
    }

    protected static void call(final Object binder, final String code,
            Flowable<Result<ResponseBody>> api, Scheduler workThread, Scheduler resultThread) {
        api.subscribeOn(workThread).observeOn(resultThread)
           .subscribe(new Consumer<Result<ResponseBody>>() {
               @Override
               public void accept(Result<ResponseBody> result) throws Exception {
                   NetHelper helper = NetHelpers.getHelper(binder.hashCode());
                   if (helper != null)
                       helper.accept(code, result);
               }
           });
    }

    protected static void call(final Object binder, final String code,
            Flowable<Result<ResponseBody>> api) {
        call(binder, code, api, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    protected static void callSync(final Object binder, final String code,
            Flowable<Result<ResponseBody>> api) {
        call(binder, code, api, Schedulers.trampoline(), Schedulers.trampoline());
    }


}
