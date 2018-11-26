package cn.yuanye1818.autils;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.view.ViewGroup;

import cn.yuanye1818.autils.compiler.annotation.BackType;
import cn.yuanye1818.autils.compiler.annotation.NetBack;
import cn.yuanye1818.autils.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.autils.core.activity.CoreActivity;
import cn.yuanye1818.autils.core.activity.adapter.more.CoreMoreAdapter;
import cn.yuanye1818.autils.core.activity.viewholder.CoreViewHolder;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.net.RetrofitManager;
import cn.yuanye1818.autils.core.utils.ViewFunc;
import cn.yuanye1818.autils.test.BeanBack;
import cn.yuanye1818.autils.test.Net;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends CoreActivity {

    private RecyclerView rv;
    private MoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);

        ViewFunc.setRecyclerViewLinearVertical(rv);

        adapter = new MoreAdapter();
        adapter.setOnLoadMore(new CoreMoreAdapter.OnLoadMore() {
            @Override
            public void onLoadMore(int page) {

            }
        });

        rv.setAdapter(adapter);


    }

    public static class MoreAdapter extends CoreMoreAdapter<MyViewHolder, User> {

        @Override
        protected void onBindItemViewHolder(MyViewHolder vh, int position) {

        }
    }

    public static class MyViewHolder extends CoreViewHolder {

        public MyViewHolder(ViewGroup viewGroup) {
            super(R.layout.row_my, viewGroup);
        }
    }


}
