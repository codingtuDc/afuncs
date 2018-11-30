package cn.yuanye1818.autils.compiler;

import java.util.Collection;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.rmi.CORBA.Util;

import cn.yuanye1818.autils.compiler.utils.Utils;

public abstract class CoreMaker extends AbstractProcessor {

    protected Elements elementUtils;
    protected Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        Utils.setTypeUtils(typeUtils);
    }

    protected boolean isNull(Collection c) {
        return c == null || c.size() <= 0;
    }

}
