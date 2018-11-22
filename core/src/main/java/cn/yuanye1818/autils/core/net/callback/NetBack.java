package cn.yuanye1818.autils.core.net.callback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class NetBack implements NetBackI {

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
