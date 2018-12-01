package cn.yuanye1818.autils.compiler;

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

import cn.yuanye1818.autils.compiler.annotation.net.Api;
import cn.yuanye1818.autils.compiler.annotation.net.BaseUrl;
import cn.yuanye1818.autils.compiler.utils.Utils;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"cn.yuanye1818.autils.compiler.annotation.net.Api"})
public class Maker00ForNet extends CoreMaker {


    private HashMap<String, String> baseUrls = new HashMap<String, String>();
    private ArrayList<FieldSpec> baseUrlFieldSpecs = new ArrayList<FieldSpec>();
    private ArrayList<FieldSpec> defaultCodeFieldSpecs = new ArrayList<FieldSpec>();
    private ArrayList<MethodSpec> methodSpecs = new ArrayList<MethodSpec>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        createNet(roundEnvironment);
        return true;
    }

    private void createNet(RoundEnvironment roundEnvironment) {
        Set<? extends Element> allApis = roundEnvironment.getElementsAnnotatedWith(Api.class);
        if (!isNull(allApis)) {
            for (Element apiElement : allApis) {
                if (apiElement instanceof TypeElement) {
                    dealApi((TypeElement) apiElement);
                }
            }
        }

        TypeSpec.Builder net = TypeSpec.classBuilder("Net");
        net.addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (int i = 0; i < baseUrlFieldSpecs.size(); i++) {
            net.addField(baseUrlFieldSpecs.get(i));
        }

        for (int i = 0; i < defaultCodeFieldSpecs.size(); i++) {
            net.addField(defaultCodeFieldSpecs.get(i));
        }

        for (int i = 0; i < methodSpecs.size(); i++) {
            net.addMethod(methodSpecs.get(i));
        }


        JavaFile netFile = JavaFile.builder(NET_PACKAGE, net.build()).build();

        try {
            netFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dealApi(TypeElement api) {
        List<? extends Element> methodElements = api.getEnclosedElements();
        if (!isNull(methodElements)) {
            for (Element methodElement : methodElements) {
                if (methodElement instanceof ExecutableElement) {
                    dealMethod(api, (ExecutableElement) methodElement);
                }
            }
        }
    }

    private void dealMethod(TypeElement api, ExecutableElement methodElement) {
        String methodName = methodElement.getSimpleName().toString();

        String staticName = Utils.getStaticName(methodName);

        defaultCodeFieldSpecs.add(FieldSpec.builder(String.class, staticName, Modifier.PUBLIC,
                                                    Modifier.STATIC, Modifier.FINAL)
                                           .initializer("\"" + methodName + "\"").build());

        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);


        List<? extends VariableElement> parameters = methodElement.getParameters();


        ClassName classApi = ClassName.bestGuess(CLASS_API);
        ClassName classService = ClassName.bestGuess(api.getQualifiedName().toString());
        ClassName classNetUtils = ClassName.bestGuess(CLASS_NET_UTILS);

        builder.returns(classApi);

        StringBuilder sb = new StringBuilder();

        sb.append("return $T.api(new $T.CreateApi() {\n");
        sb.append("  @Override\n");
        sb.append("  public $T<$T<$T>> create($T retrofit) {\n");
        sb.append("    return retrofit.create($T.class).$N(");


        ArrayList ps = new ArrayList();
        ps.add(classNetUtils);
        ps.add(classNetUtils);
        ps.add(ClassName.bestGuess(CLASS_FLOWABLE));
        ps.add(ClassName.bestGuess(CLASS_RESULT));
        ps.add(ClassName.bestGuess(CLASS_RESPONSE_BODY));
        ps.add(ClassName.bestGuess(CLASS_RETROFIT));
        ps.add(classService);
        ps.add(methodName);


        if (!isNull(parameters)) {

            for (int i = 0; i < parameters.size(); i++) {

                VariableElement methodParameter = parameters.get(i);

                TypeMirror methodParameterType = methodParameter.asType();

                TypeName typeName = TypeName.get(methodParameterType);

                String paramName = methodParameter.getSimpleName().toString();

                builder.addParameter(typeName, paramName, Modifier.FINAL);

                ps.add(paramName);

                if (i == 0) {
                    sb.append("$N");
                } else {
                    sb.append(", $N");
                }

            }
        }


        sb.append(");\n");
        sb.append("  }\n");
        sb.append("}, $N");

        ps.add(staticName);


        BaseUrl baseUrl = api.getAnnotation(BaseUrl.class);

        String url = "";
        if (baseUrl != null) {
            String value = baseUrl.value();

            String valueName = null;

            if (!baseUrls.containsKey(value)) {

                valueName = "BASE_URL_" + baseUrls.keySet().size();

                baseUrlFieldSpecs.add(FieldSpec.builder(String.class, valueName, Modifier.PRIVATE,
                                                        Modifier.STATIC, Modifier.FINAL)
                                               .initializer("\"" + value + "\"").build());

                baseUrls.put(value, valueName);
            } else {
                valueName = baseUrls.get(value);
            }

            url += ", $N";
            ps.add(valueName);
        }

        sb.append(url);
        sb.append(");\n");


        builder.addCode(sb.toString(), ps.toArray());

        methodSpecs.add(builder.build());

    }

}
