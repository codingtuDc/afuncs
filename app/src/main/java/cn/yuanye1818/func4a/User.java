package cn.yuanye1818.func4a;

import cn.yuanye1818.func4a.core.bean.CoreBean;

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
