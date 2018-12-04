package cn.yuanye1818.func4a.test;

import java.io.IOException;

import cn.yuanye1818.func4a.core.json.JsonFunc;
import cn.yuanye1818.func4a.core.log.Logs;
import cn.yuanye1818.func4a.core.net.NetBackI;
import cn.yuanye1818.func4j.ClassFunc;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public abstract class BeanBack<T> implements NetBackI {

    @Override
    public void accept(String code, Result<ResponseBody> result) {

        if (result.isError()) {
            Throwable error = result.error();
            back(error.getMessage(), null);
        } else {

            try {
                String json = result.response().body().string();

                cn.yuanye1818.func4a.Result result1 = JsonFunc
                        .toBean(cn.yuanye1818.func4a.Result.class, json);

                String data = result1.getData();

                T t = JsonFunc.toBean((Class<T>) ClassFunc.getParameterizedType(this, 0), data);

                back("", t);

            } catch (IOException e) {
                Logs.w(e);
            }


        }
    }

    public abstract void back(String message, T t);
}
