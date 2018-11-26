package cn.yuanye1818.autils.core.net;

import java.lang.reflect.Constructor;

import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.global.AutilsConfigs;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

public class NetUtils extends RetrofitManager {

    public static NetBackI bindNet(Object binder) {
        try {
            String helperName = binder.getClass().getName() + "_Helper";
            Constructor<NetBackI> constructor = (Constructor<NetBackI>) Class.forName(helperName)
                                                                             .getConstructor(
                                                                                     binder.getClass());
            return constructor.newInstance(binder);
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

    public static Api api(CreateApi createApi, String code) {
        return api(createApi, code, AutilsConfigs.configs().getBaseUrl());
    }

    public static Api api(CreateApi createApi, String code, String baseUrl) {
        return new ApiSub(createApi, code, baseUrl);
    }

    public static interface CreateApi {
        public Flowable<Result<ResponseBody>> create(Retrofit retrofit);
    }
}
