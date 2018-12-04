package cn.yuanye1818.func4a.core.compiler.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import cn.yuanye1818.func4a.core.compiler.annotation.activity.Launcher;
import cn.yuanye1818.func4a.core.compiler.annotation.net.BackType;
import cn.yuanye1818.func4a.core.compiler.annotation.net.NetBack;
import cn.yuanye1818.func4a.core.compiler.builder.ClassBuilder;
import cn.yuanye1818.func4a.core.compiler.builder.MethodBuilder;
import cn.yuanye1818.func4a.core.compiler.element.CE;
import cn.yuanye1818.func4a.core.compiler.element.PE;
import cn.yuanye1818.func4j.ls.Ls;
import cn.yuanye1818.func4j.ls.each.Each;

import static cn.yuanye1818.func4j.ls.Ls.ls;

public class Utils {

    private static Types typeUtils;
    private static ProcessingEnvironment processingEnv;


    public static void setProcessingEnv(ProcessingEnvironment processingEnv) {
        Utils.processingEnv = processingEnv;
    }

    public static String getHeroName(CE ce) {
        return ce.fullName() + "_Hero";
    }

    public static boolean containsClass(List<ClassBuilder> cs, String c) {
        return cs.contains(new ClassBuilder(c));
    }

    public static ClassBuilder getClass(List<ClassBuilder> cs, String className) {
        return cs.get(cs.indexOf(new ClassBuilder(className)));
    }

    public static String getNetBack(NetBack netBack) {
        String back = null;
        try {
            back = netBack.value().getName();
        } catch (MirroredTypeException mte) {
            TypeMirror classTypeMirror = mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) typeUtils.asElement(classTypeMirror);
            back = classTypeElement.getQualifiedName().toString();
        }
        if (Void.class.getName().equals(back)) {
            back = null;
        }
        return back;
    }

    public static void getLauncherParams(Launcher launcher, Each<String> each) {
        try {
            launcher.paramClasses();
        } catch (MirroredTypesException mte) {
            List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();

            Ls.ls(typeMirrors, (position, typeMirror) -> {
                each.each(position, typeMirror.toString());
                //                TypeElement classTypeElement = (TypeElement) typeUtils.asElement(typeMirror);
                //                each.each(position, classTypeElement.getQualifiedName().toString());
                return false;
            });
        }
    }


    public static void setTypeUtils(Types typeUtils) {
        Utils.typeUtils = typeUtils;
    }


    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().length() <= 0;
    }

    public static void addMethodParamDefineCode(final MethodBuilder accept,
            List<? extends VariableElement> params) {
        ls(params, new Each<VariableElement>() {
            @Override
            public boolean each(int position, VariableElement variableElement) {
                PE pe = new PE(variableElement);
                if (position != 0)
                    accept.builder().addCode(", ");
                accept.builder().addCode("$T $N", ClassName.bestGuess(pe.typeName()), pe.name());
                return false;
            }
        });
    }

    public static void addUseMethodParamCode(final MethodBuilder accept,
            List<? extends VariableElement> params) {
        ls(params, new Each<VariableElement>() {
            @Override
            public boolean each(int position, VariableElement variableElement) {
                if (position != 0)
                    accept.builder().addCode(", ");
                accept.builder().addCode("$N", new PE(variableElement).name());

                return false;
            }
        });
    }

    public static void addClassTypeCode(final MethodBuilder accept,
            List<? extends VariableElement> params) {
        ls(params, e -> {
            return e.getAnnotation(BackType.class) == null;
        }, (position, isLast, e) -> {
            if (position == 0) {
                accept.builder().addCode("<");
            } else {
                accept.builder().addCode(", ");
            }

            accept.builder().addCode("$T", ClassName.bestGuess(new PE(e).typeName()));

            if (isLast) {
                accept.builder().addCode(">");
            }
            return false;
        });
    }

    public static String nameFirstUpper(String name) {
        char[] chars = name.toCharArray();
        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] -= 32;
        }
        return new String(chars);
    }

    public static void build(ClassBuilder builder) {
        try {
            JavaFile.builder(builder.getPackgeName(), builder.build()).build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void throwE(String s) {
        throw new RuntimeException(s);
    }
}
