package cn.yuanye1818.autils.core.net;

import android.os.Looper;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class ApiSub implements Api {
    private Flowable<Result<ResponseBody>> flowable;
    private String m;

    public ApiSub(Flowable<Result<ResponseBody>> flowable, String m) {
        this.flowable = flowable;
        this.m = m;
    }

    @Override
    public Api m(String m) {
        this.m = m;
        return this;
    }

    @Override
    public void main(NetBackI helper) {
        main(helper, false);
    }

    @Override
    public void main(NetBackI helper, boolean isForceNewThread) {
        run(helper, AndroidSchedulers.mainThread(), isForceNewThread);
    }

    @Override
    public void io(NetBackI helper) {
        io(helper, false);
    }

    @Override
    public void io(NetBackI helper, boolean isForceNewThread) {
        run(helper, Schedulers.trampoline(), isForceNewThread);
    }

    private void run(final NetBackI helper, Scheduler resultScheduler, boolean isForceNewThread) {

        Scheduler work = null;
        if (isForceNewThread || Looper.getMainLooper().getThread() == Thread.currentThread()) {
            work = Schedulers.io();
        } else {
            work = Schedulers.trampoline();
        }

        flowable.subscribeOn(work).observeOn(resultScheduler)
                .subscribe(new Consumer<Result<ResponseBody>>() {
                    @Override
                    public void accept(Result<ResponseBody> result) throws Exception {
                        if (helper != null)
                            helper.accept(m, result);
                    }
                });

    }

}
