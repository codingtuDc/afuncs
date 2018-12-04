package cn.yuanye1818.func4a.core.bean;

import cn.yuanye1818.func4a.core.json.JsonFunc;

public class CoreBean {

    @Override
    public String toString() {
        return JsonFunc.toJson(this);
    }
}
