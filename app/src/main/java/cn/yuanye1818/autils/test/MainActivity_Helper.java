package cn.yuanye1818.autils.test;


import com.yuanye1818.autils.net.Net;

import cn.yuanye1818.autils.MainActivity;
import cn.yuanye1818.autils.core.net.NetHelper;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class MainActivity_Helper implements NetHelper {

    private MainActivity act;

    public MainActivity_Helper(MainActivity act) {
        this.act = act;
    }

    @Override
    public void accept(String code, Result<ResponseBody> result) {

//        if (result.isError()) {
//            netBack.onFailure(null, result.error());
//        } else {
//            netBack.onResponse(null, result.response());
//        }
//


        if (Net.GET_ALBUM_DETAIL.equals(code)) {
            act.userBack(0, null, null);
        }
    }
}
