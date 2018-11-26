package cn.yuanye1818.autils.core.bean;

import cn.yuanye1818.autils.core.json.JsonFunc;

public class CoreBean {

    @Override
    public String toString() {
        return JsonFunc.toJson(this);
    }
}
