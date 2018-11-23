package cn.yuanye1818.autils.core.net;

import java.lang.reflect.Constructor;

import cn.yuanye1818.autils.core.log.Logs;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
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

    public static Api api(Flowable<Result<ResponseBody>> flowable, String code) {
        return new ApiSub(flowable, code);
    }
}
