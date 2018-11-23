package cn.yuanye1818.autils.net_utils;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import cn.yuanye1818.autils.net_utils_annotation.Api;
import cn.yuanye1818.autils.net_utils_annotation.BaseUrl;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"cn.yuanye1818.autils.net_utils_annotation.Api",
        "cn.yuanye1818.autils.net_utils_annotation.NetBack"})
public class NetMaker extends MyProcessor {

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
