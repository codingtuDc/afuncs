package cn.yuanye1818.autils.core.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

import cn.yuanye1818.autils.core.activity.viewholder.CoreViewHolder;
import cn.yuanye1818.autils.core.log.Logs;
import cn.yuanye1818.autils.core.utils.ClassFunc;

public abstract class CoreAdapter<VH extends CoreViewHolder> extends RecyclerView.Adapter<VH> {


    protected CoreViewHolder createViewHolder(int p, ViewGroup viewGroup) {
        try {
            Constructor<CoreViewHolder> constructor = ((Class) ClassFunc
                    .getParameterizedType(this, p)).getConstructor(ViewGroup.class);
            return constructor.newInstance(viewGroup);
        } catch (Exception e) {
            Logs.w(e);
            return null;
        }
    }


    /***************************************
     *
     *  判断最后一项
     *
     ***************************************/

    protected boolean isLastPosition(int position) {
        return position == (getItemCount() - 1);
    }

}
