package cn.yuanye1818.autils.core.net;

import java.util.HashMap;
import java.util.Map;

import cn.yuanye1818.autils.global.App;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {

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


}
