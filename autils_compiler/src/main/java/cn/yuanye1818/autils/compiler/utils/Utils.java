package cn.yuanye1818.autils.compiler.utils;

import com.squareup.javapoet.ClassName;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import cn.yuanye1818.autils.compiler.annotation.net.BackType;
import cn.yuanye1818.autils.compiler.annotation.net.NetBack;
import cn.yuanye1818.autils.compiler.builder.ClassBuilder;
import cn.yuanye1818.autils.compiler.builder.MethodBuilder;
import cn.yuanye1818.autils.compiler.element.CE;
import cn.yuanye1818.autils.compiler.element.PE;

import static cn.yuanye1818.autils.compiler.utils.ListUtils.ls;

public class Utils {

    private static Types typeUtils;

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


    public static void setTypeUtils(Types typeUtils) {
        Utils.typeUtils = typeUtils;
    }


    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().length() <= 0;
    }

    public static void addMethodParamDefineCode(final MethodBuilder accept,
            List<? extends VariableElement> params) {
        ls(params, new ListUtils.Each<VariableElement>() {
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
        ls(params, new ListUtils.Each<VariableElement>() {
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

        ls(params, new ListUtils.EachWithFilter<VariableElement>() {
            @Override
            public boolean each(int position, boolean isLast, VariableElement e) {

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
            }

            @Override
            public boolean filter(VariableElement e) {
                return e.getAnnotation(BackType.class) == null;
            }
        });
    }

}
