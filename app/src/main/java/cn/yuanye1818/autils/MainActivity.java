package cn.yuanye1818.autils;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.yuanye1818.autils.compiler.annotation.net.BackType;
import cn.yuanye1818.autils.compiler.annotation.net.NetBack;
import cn.yuanye1818.autils.compiler.annotation.onactivityresult.OnResult;
import cn.yuanye1818.autils.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.autils.compiler.annotation.permission.PermissionCheck;
import cn.yuanye1818.autils.compiler.annotation.view.FindView;
import cn.yuanye1818.autils.core.activity.CoreActivity;
import cn.yuanye1818.autils.core.activity.adapter.more.CoreMoreAdapter;
import cn.yuanye1818.autils.core.activity.viewholder.CoreViewHolder;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.net.Net;
import cn.yuanye1818.autils.core.permission.PermissionChecker;
import cn.yuanye1818.autils.core.utils.ViewFunc;
import cn.yuanye1818.autils.test.BeanBack;
import cn.yuanye1818.autils.test.RequestCode;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends CoreActivity {

    @FindView(R.id.rv)
    RecyclerView rv;
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<User> users = new ArrayList<User>();
                        users.add(new User());
                        users.addAll(users);
                        users.addAll(users);
                        users.addAll(users);
                        users.addAll(users);
                        users.addAll(users);
                        users.addAll(users);
                        users.addAll(users);


                        adapter.updateItem(users);
                    }
                }, 1000);
            }
        });
        adapter.setOnClickListener(hero);
        rv.setAdapter(adapter);

        Net.getAlbumDetail("2005").main(hero);

        PermissionChecker.checkStoreFile(hero);

    }

    @OnResult(RequestCode.STORY)
    public void storyBack(Intent data) {

    }
    @OnResult(RequestCode.STORY_LIST)
    public void storyListBack(Intent data) {

    }


    @PermissionCheck(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void storeFile() {

    }


    @NetBack
    public void getAlbumDetail() {

    }

    @NetBack
    public void getAlbumDetail(Throwable error, Response<ResponseBody> response) {

    }

    @NetBack(BeanBack.class)
    public void getAlbumDetail(String messsage, @BackType User user) {

    }

    @ClickView(R.id.ll)
    public void clickLl() {
        Logs.line("clickLl");
    }

    @ClickView(R.id.avatarIv)
    public void clickAvatar(View view, int position, User user) {
        Logs.line("clickAvatar:" + view + " " + position + " " + user);
    }

    @ClickView(R.id.nameTv)
    public void clickName(View view, User user) {
        Logs.line("clickName:" + view + " user:" + user);
    }

    public static class MoreAdapter extends CoreMoreAdapter<MyViewHolder, User> {

        @Override
        protected void onBindItemViewHolder(MyViewHolder vh, int position) {
            setOnClick(vh.ll, position, ts.get(position));
            setOnClick(vh.avatarIv, position, ts.get(position));
            setOnClick(vh.nameTv, position, ts.get(position));
        }
    }

    public static class MyViewHolder extends CoreViewHolder {

        LinearLayout ll;
        ImageView avatarIv;
        TextView nameTv;

        public MyViewHolder(ViewGroup viewGroup) {
            super(R.layout.row_my, viewGroup);
            ll = itemView.findViewById(R.id.ll);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }


}
