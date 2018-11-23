package cn.yuanye1818.autils.compiler;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({NetMaker.CLASS_API, NetMaker.CLASS_NET_BACK})
public class NetMaker extends MyProcessor {

    public static final String CLASS_API = "cn.yuanye1818.autils.compiler.annotation.Api";
    public static final String CLASS_NET_BACK = "cn.yuanye1818.autils.compiler.annotation.NetBack";

    private NetMaker01ForNet netMaker01ForNet;
    private NetMaker02ForHelper netMaker02ForHelper;

    public NetMaker() {
        this.netMaker01ForNet = new NetMaker01ForNet();
        this.netMaker02ForHelper = new NetMaker02ForHelper();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.netMaker01ForNet.init(processingEnvironment);
        this.netMaker02ForHelper.init(processingEnvironment);

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        netMaker01ForNet.process(set, roundEnvironment);
        netMaker02ForHelper.process(set, roundEnvironment);
        return true;
    }


}
