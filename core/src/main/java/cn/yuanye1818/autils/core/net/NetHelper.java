package cn.yuanye1818.autils.core.net;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public interface NetHelper {
    void accept(String code, Result<ResponseBody> result);
}
