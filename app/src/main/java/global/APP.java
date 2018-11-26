package global;

import java.util.List;

import cn.yuanye1818.autils.core.json.jsonholder.JsonHolder;
import cn.yuanye1818.autils.core.net.RetrofitManager;
import cn.yuanye1818.autils.global.App;
import cn.yuanye1818.autils.global.AutilsConfigs;

public class APP extends App {

    @Override
    public AutilsConfigs createConfigs() {
        return new Configs();
    }


}
