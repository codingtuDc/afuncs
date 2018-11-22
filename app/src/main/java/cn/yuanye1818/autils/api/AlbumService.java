package cn.yuanye1818.autils.api;

import cn.yuanye1818.autils.net_utils_annotation.Api;
import cn.yuanye1818.autils.net_utils_annotation.BaseUrl;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Api
public interface AlbumService {

    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> getAlbumDetail(@Path("id") String id);

}
