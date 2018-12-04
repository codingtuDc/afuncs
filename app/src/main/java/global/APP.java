package global;

import cn.yuanye1818.func4a.global.App;
import cn.yuanye1818.func4a.global.CoreConfigs;

public class APP extends App {

    @Override
    public CoreConfigs createConfigs() {
        return new Configs();
    }

    @Override
    public String getPicDirName() {
        return "AUtils";
    }


}
