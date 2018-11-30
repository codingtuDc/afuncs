package cn.yuanye1818.autils;

import cn.yuanye1818.autils.core.bean.CoreBean;
import cn.yuanye1818.autils.core.json.JsonFunc;

public class User extends CoreBean {

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
