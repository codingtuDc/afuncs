package cn.yuanye1818.func4a.test;

import cn.yuanye1818.func4a.api.AlbumService;
import cn.yuanye1818.func4a.core.net.API;
import cn.yuanye1818.func4a.core.net.NetUtils;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

public class Net {

    public static final String GET_ALBUM_DETAIL = "getAlbumDetail";


    public static API getAlbumDetail(final String id) {
        return NetUtils.api(new NetUtils.CreateApi() {
            @Override
            public Flowable<Result<ResponseBody>> create(Retrofit retrofit) {
                return retrofit.create(AlbumService.class).getAlbumDetail(id);
            }
        }, GET_ALBUM_DETAIL);
    }

}
