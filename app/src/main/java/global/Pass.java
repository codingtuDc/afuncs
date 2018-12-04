package global;

import android.content.Intent;

import cn.yuanye1818.func4a.User;
import cn.yuanye1818.func4a.core.json.JsonFunc;

public class Pass {
    public static final String USER = "user";
    public static final String CODE = "code";

    public static final User user(Intent data) {
        return JsonFunc.toBean(User.class, data.getStringExtra(USER));
    }

    public static final int code(Intent data) {
        return data.getIntExtra(CODE, -1);
    }
    public static final String userStr(Intent data) {
        return data.getStringExtra(USER);
    }
}
