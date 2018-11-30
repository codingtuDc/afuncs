package cn.yuanye1818.autils.test;

import android.view.View;

import cn.yuanye1818.autils.MainActivity;
import cn.yuanye1818.autils.R;
import cn.yuanye1818.autils.User;
import cn.yuanye1818.autils.core.hero.Hero;
import cn.yuanye1818.autils.core.log.Logs;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class MainActivity_Hero extends Hero {

    private MainActivity act;

    public MainActivity_Hero(MainActivity act) {
        this.act = act;
        setOnclickListener(R.id.ll);
        setOnclickListener(R.id.ll);
        setOnclickListener(R.id.ll);
    }

    private void setOnclickListener(int id) {
        try {
            this.act.findViewById(id).setOnClickListener(this);
        } catch (Exception e) {
            Logs.w(e);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == 123) {
            act.clickLl();
        } else if (id == 12356) {
            act.clickAvatar(v, (int) v.getTag(R.id.tag_position), (User) v.getTag(R.id.tag_obj));
        }
    }

    @Override
    public void accept(String code, Result<ResponseBody> result) {

        if ("getAlbumDetail".equals(code)) {
            new BeanBack<User>() {
                @Override
                public void back(String message, User user) {
                    act.getAlbumDetail(message, user);
                }
            }.accept(code, result);
        }


    }
}
