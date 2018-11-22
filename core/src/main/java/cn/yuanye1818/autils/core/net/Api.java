package cn.yuanye1818.autils.core.net;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class Api {
    private Flowable<Result<ResponseBody>> flowable;
    private String code;

    public Api(Flowable<Result<ResponseBody>> flowable, String code) {
        this.flowable = flowable;
        this.code = code;
    }

    public Api code(String code) {
        this.code = code;
        return this;
    }

    public void run(Object binder) {
        NetUtils.call(binder, this.code, flowable);
    }

    public void sync(Object binder) {
        NetUtils.callSync(binder, this.code, flowable);
    }

    public static Api create(Flowable<Result<ResponseBody>> flowable, String defaultCode) {
        return new Api(flowable, defaultCode);
    }

}
