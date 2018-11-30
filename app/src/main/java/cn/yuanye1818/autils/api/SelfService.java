package cn.yuanye1818.autils.api;

import cn.yuanye1818.autils.User;
import cn.yuanye1818.autils.compiler.annotation.net.Api;
import cn.yuanye1818.autils.compiler.annotation.net.BaseUrl;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Api
@BaseUrl("https://bookgood.wismoly.com/app/api/v1/")
public interface SelfService {

    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> selfDetailOne(@Path("id") String id,User user);
    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> selfDetailTwo(@Path("id") String id);
    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> selfDetailThree(@Path("id") String id,int age);

}
