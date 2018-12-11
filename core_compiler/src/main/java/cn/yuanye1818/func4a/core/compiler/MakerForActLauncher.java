package cn.yuanye1818.func4a.core.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import cn.yuanye1818.func4a.core.compiler.builder.ClassBuilder;
import cn.yuanye1818.func4a.core.compiler.builder.FieldBuilder;
import cn.yuanye1818.func4a.core.compiler.builder.MethodBuilder;
import cn.yuanye1818.func4a.core.compiler.element.CE;
import cn.yuanye1818.func4a.core.compiler.utils.Utils;
import cn.yuanye1818.func4a.func4j.StringFunc;
import cn.yuanye1818.func4a.func4j.ls.each.Each;

import static cn.yuanye1818.func4a.func4j.ls.Ls.ls;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({CoreMaker.CLASS_LAUNCHER})
public class MakerForActLauncher extends CoreMaker {

    private ClassBuilder classBuilder;
    private ClassBuilder requestCodeClassBuilder;

    public static int REQUEST_CODE = 0;
    private ClassBuilder passClassBuilder;

    private StringBuilder sb = new StringBuilder();

    private ArrayList<String> passParams = new ArrayList<String>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        ls(roundEnvironment.getElementsAnnotatedWith(Launcher.class), (position, e) -> {

            if (e instanceof TypeElement) {
                dealLauncher(((TypeElement) e));
            }

            return false;
        });

        if (requestCodeClassBuilder != null)
            Utils.build(requestCodeClassBuilder);
        if (passClassBuilder != null)
            Utils.build(passClassBuilder);
        Utils.build(classBuilder);

        return true;
    }

    private void dealLauncher(TypeElement e) {
        CE ce = new CE(e);
        if (classBuilder == null) {
            classBuilder = new ClassBuilder(CLASS_ACT_LUNCHER);
        }

        String simpleName = ce.simpleName();
        String methodName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
        MethodBuilder mb = new MethodBuilder(methodName);

        mb.publicMethod().staticMethod().finalMethod();
        mb.addParameter(activityClass, "act");

        Launcher annotation = ce.getAnnotation(Launcher.class);

        mb.addCodeLine("    $T intent = new $T(act, $T.class);", intentClass, intentClass,
                       ce.className());


        Utils.getAnnotationClasses(() -> annotation.paramClasses(), new Each<String>() {
            @Override
            public boolean each(int position, String s) {
                String paramName = annotation.paramNames()[position];

                if (passClassBuilder == null) {
                    passClassBuilder = new ClassBuilder(CLASS_PASS);
                }

                String staticName = StringFunc.getStaticName(paramName);

                FieldBuilder fb = new FieldBuilder(String.class, staticName);
                fb.publicField().staticField().finalField();
                fb.initializer("$S", paramName);

                passClassBuilder.addField(fb);

                MethodBuilder pmb = null;
                if (!passParams.contains(paramName)) {
                    passParams.add(paramName);
                    pmb = new MethodBuilder(paramName);
                    pmb.publicMethod().staticMethod().finalMethod();
                    pmb.addParameter(intentClass,"data");
                }


                if ("int".equals(s)) {
                    setPmb(pmb, TypeName.INT, "return data.getIntExtra($T.$N, -1);", passClass,
                           staticName);
                    mb.addParameter(TypeName.INT, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("double".equals(s)) {
                    setPmb(pmb, TypeName.DOUBLE, "return data.getDoubleExtra($T.$N, -1);",
                           passClass, staticName);
                    mb.addParameter(TypeName.DOUBLE, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("long".equals(s)) {
                    setPmb(pmb, TypeName.LONG, "return data.getLongExtra($T.$N, -1);", passClass,
                           staticName);
                    mb.addParameter(TypeName.LONG, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("boolean".equals(s)) {
                    setPmb(pmb, TypeName.BOOLEAN, "return data.getBooleanExtra($T.$N, false);",
                           passClass, staticName);
                    mb.addParameter(TypeName.BOOLEAN, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("byte".equals(s)) {
                    mb.addParameter(TypeName.BYTE, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("char".equals(s)) {
                    mb.addParameter(TypeName.CHAR, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("float".equals(s)) {

                    setPmb(pmb, TypeName.FLOAT, "return data.getFloatExtra($T.$N, -1f);", passClass,
                           staticName);

                    mb.addParameter(TypeName.FLOAT, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if ("short".equals(s)) {

                    mb.addParameter(TypeName.SHORT, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else if (String.class.getName().equals(s)) {
                    setPmb(pmb, ClassName.bestGuess(String.class.getName()),
                           "return data.getStringExtra($T.$N);", passClass, staticName);
                    mb.addParameter(String.class, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $N);", passClass, staticName,
                                   paramName);
                } else {
                    ClassName className = ClassName.bestGuess(s);
                    setPmb(pmb, className,
                           "return $T.toBean($T.class, data.getStringExtra($T.$N));", jsonFuncClass,
                           className, passClass, staticName);
                    mb.addParameter(className, paramName);
                    mb.addCodeLine("    intent.putExtra($T.$N, $T.toJson($N));", passClass,
                                   staticName, jsonFuncClass, paramName);

                }


                if (pmb != null)
                    passClassBuilder.addMethod(pmb);

                return false;
            }
        });


        String requestCodeName = StringFunc.getStaticName(simpleName);
        if (requestCodeClassBuilder == null) {
            requestCodeClassBuilder = new ClassBuilder(CLASS_CODE_4_REQUEST);
        }

        FieldBuilder fb = new FieldBuilder(TypeName.INT, requestCodeName);
        fb.publicField().staticField().finalField();
        fb.initializer("$L", REQUEST_CODE++);

        requestCodeClassBuilder.addField(fb);
        mb.addCodeLine("    $T.startActivityForResult(act, intent, $T.$N);", actFuncClass,
                       code4RequestClass, requestCodeName);


        classBuilder.addMethod(mb);

    }

    private void setPmb(MethodBuilder pmb, TypeName className, String format, Object... args) {
        if (pmb == null)
            return;
        pmb.returns(className);
        pmb.addCodeLine(format, args);
    }


}
