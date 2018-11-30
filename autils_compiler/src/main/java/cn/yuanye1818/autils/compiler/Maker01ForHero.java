package cn.yuanye1818.autils.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.ArrayList;
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
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

import cn.yuanye1818.autils.compiler.annotation.net.NetBack;
import cn.yuanye1818.autils.compiler.annotation.onclick.ClickTag;
import cn.yuanye1818.autils.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.autils.compiler.annotation.view.FindView;
import cn.yuanye1818.autils.compiler.element.CE;
import cn.yuanye1818.autils.compiler.builder.ClassBuilder;
import cn.yuanye1818.autils.compiler.element.ME;
import cn.yuanye1818.autils.compiler.builder.MethodBuilder;
import cn.yuanye1818.autils.compiler.element.PE;
import cn.yuanye1818.autils.compiler.utils.ListUtils;
import cn.yuanye1818.autils.compiler.utils.Utils;

import static cn.yuanye1818.autils.compiler.utils.ListUtils.Each;
import static cn.yuanye1818.autils.compiler.utils.ListUtils.ls;
import static java.util.Objects.requireNonNull;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"cn.yuanye1818.autils.compiler.annotation.onclick.ClickView"})
public class Maker01ForHero extends CoreMaker {

    public static final String METHOD_SET_ON_CLICK = "setOnClick";
    public static final String METHOD_ON_CLICK = "onClick";
    public static final String METHOD_ACCEPT = "accept";

    public static final String CLASS_HERO = "cn.yuanye1818.autils.core.hero.Hero";
    public static final String CLASS_VIEW = "android.view.View";
    public static final String CLASS_RESULT = "retrofit2.adapter.rxjava2.Result";
    public static final String CLASS_RESPONSE_BODY = "okhttp3.ResponseBody";
    public static final String CLASS_LOGS = "cn.yuanye1818.autils.core.log.Logs";
    public static final String CLASS_THROWABLE = Throwable.class.getName();
    public static final String CLASS_RESPONSE = "retrofit2.Response";
    public static final String CLASS_OVERRIDE = Override.class.getName();

    public static final ClassName heroClass = ClassName.bestGuess(CLASS_HERO);
    public static final ClassName viewClass = ClassName.bestGuess(CLASS_VIEW);
    public static final ClassName stringClass = ClassName.bestGuess(String.class.getName());
    public static final ClassName resultClass = ClassName.bestGuess(CLASS_RESULT);
    public static final ClassName responseBodyClass = ClassName.bestGuess(CLASS_RESPONSE_BODY);
    public static final ClassName logsClass = ClassName.bestGuess(CLASS_LOGS);
    public static final ClassName throwableClass = ClassName.bestGuess(CLASS_THROWABLE);
    public static final ClassName responseClass = ClassName.bestGuess(CLASS_RESPONSE);
    public static final ClassName overrideClass = ClassName.bestGuess(CLASS_OVERRIDE);

    private ArrayList<ClassBuilder> cs = new ArrayList<ClassBuilder>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ls(roundEnvironment.getElementsAnnotatedWith(ClickView.class), new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealClickViewMethodElement((ExecutableElement) element);
                }
                return false;
            }
        });

        ls(roundEnvironment.getElementsAnnotatedWith(NetBack.class), new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealNetBackMethodElement((ExecutableElement) element);
                }
                return false;
            }
        });
        ls(roundEnvironment.getElementsAnnotatedWith(FindView.class), new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof VariableElement) {
                    dealFindViewElement((VariableElement) element);
                }
                return false;
            }
        });

        ls(cs, new Each<ClassBuilder>() {
            @Override
            public boolean each(int position, final ClassBuilder classBuilder) {

                List<MethodBuilder> methodBuilders = classBuilder.getMethodBuilders();

                ls(methodBuilders, new Each<MethodBuilder>() {
                    @Override
                    public boolean each(int position, MethodBuilder methodBuilder) {
                        classBuilder.builder().addMethod(methodBuilder.builder().build());
                        return false;
                    }
                });

                classBuilder.builder().addMethod(classBuilder.constructorBuilder().build());


                JavaFile javaFile = JavaFile
                        .builder(classBuilder.getPackgeName(), classBuilder.build()).build();
                try {
                    javaFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        return true;
    }

    private void dealFindViewElement(VariableElement e) {
        PE pe = new PE(e);
        CE ce = new CE((TypeElement) pe.e().getEnclosingElement());

        ClassBuilder heroClass = getClass(ce, Utils.getHeroName(ce));

        Set<Modifier> modifiers = pe.e().getModifiers();
        if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.STATIC)) {
            throw new RuntimeException("字段 " + pe.name() + " 不能为私有或者静态方法");
        }


        MethodSpec.Builder constructorBuilder = heroClass.constructorBuilder();

        FindView annotation = pe.e().getAnnotation(FindView.class);

        constructorBuilder.addCode(
                "this.act." + pe.name() + " = this.act.findViewById(" + annotation
                        .value() + ");\n");

    }

    private void dealNetBackMethodElement(ExecutableElement e) {
        final ME me = new ME(e);
        CE ce = me.ce();
        final ClassBuilder heroClass = getClass(ce, Utils.getHeroName(ce));

        final MethodBuilder accept = heroClass.getMethod(METHOD_ACCEPT);
        NetBack netBack = me.e().getAnnotation(NetBack.class);

        Set<Modifier> modifiers = me.e().getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)) {
            throw new RuntimeException("回调方法 " + me.name() + " 必须为公有方法");
        }

        final List<? extends VariableElement> params = me.e().getParameters();

        String netBackHandler = Utils.getNetBack(netBack);

        if (Utils.isTextEmpty(netBackHandler)) {
            accept.builder()
                  .addCode("if ($S.equals(code)) {\n  this.act.$N(", me.name(), me.name());
            ls(params, new Each<VariableElement>() {
                @Override
                public boolean each(int position, VariableElement e) {

                    if (position != 0)
                        accept.builder().addCode(", ");

                    PE pe = new PE(e);
                    if (CLASS_THROWABLE.equals(pe.typeName())) {
                        accept.builder().addCode("result.error()");
                    } else if (CLASS_RESPONSE
                            .equals(pe.typeName()) || (CLASS_RESPONSE + "<" + CLASS_RESPONSE_BODY + ">")
                            .equals(pe.typeName())) {
                        accept.builder().addCode("result.response()");
                    } else {
                        throw new RuntimeException(pe.typeName() + "方法 " + me
                                .name() + " 参数有误。\n如果没有指定解析器，那么参数为空或者只能为一下两个参数的任意组合:\n" + "(Throwable error, Response<ResponseBody> response)");
                    }
                    return false;
                }
            });
            accept.builder().addCode(");\n}\n");
        } else {
            ClassName netBackClassName = ClassName.bestGuess(netBackHandler);

            accept.builder().addCode("if ($S.equals(code)) {\n", me.name());


            accept.builder().addCode("  new $T", netBackClassName);

            Utils.addClassTypeCode(accept, params);

            accept.builder().addCode("() {\n    @$T\n    public void back(", overrideClass);

            Utils.addMethodParamDefineCode(accept, params);

            accept.builder().addCode(") {\n      act.$N(", me.name());

            Utils.addUseMethodParamCode(accept, params);

            accept.builder().addCode(");\n    }\n  }.accept(code, result);\n}\n");

        }


    }

    private void dealClickViewMethodElement(ExecutableElement e) {
        final ME me = new ME(e);
        CE ce = me.ce();
        final ClassBuilder heroClass = getClass(ce, Utils.getHeroName(ce));

        final MethodBuilder onClick = heroClass.getMethod(METHOD_ON_CLICK);
        final ClickView clickView = me.e().getAnnotation(ClickView.class);
        int[] value = clickView.value();
        ls(value, new Each<Integer>() {
            @Override
            public boolean each(int position, Integer id) {

                heroClass.constructorBuilder().addCode(METHOD_SET_ON_CLICK + "(" + id + ");\n");

                List<? extends VariableElement> parameters = me.e().getParameters();

                if (ListUtils.count(parameters) > 0) {

                    onClick.builder().addCode("if (id == $L) {\n", id);

                    onClick.builder().addCode("  act." + me.e().getSimpleName() + "(");

                    ls(parameters, new Each<VariableElement>() {
                        @Override
                        public boolean each(int position, VariableElement variableElement) {

                            PE pe = new PE(variableElement);

                            String typeName = pe.typeName();


                            if (position != 0) {
                                onClick.builder().addCode(", ");
                            }

                            if (CLASS_VIEW.equals(typeName)) {
                                onClick.builder().addCode("v");
                            } else if ("int".equals(typeName)) {
                                onClick.builder().addCode("(int) v.getTag(R.id.tag_position)");
                            } else {
                                ClickTag annotation = pe.e().getAnnotation(ClickTag.class);

                                if (annotation == null) {
                                    onClick.builder().addCode("($T) v.getTag(R.id.tag_obj)",
                                                              ClassName.bestGuess(typeName));
                                } else {

                                    onClick.builder().addCode("($T) v.getTag($L)",
                                                              ClassName.bestGuess(typeName),
                                                              annotation.value());
                                }
                            }


                            return false;
                        }
                    });

                    onClick.builder().addCode(");\n");

                    onClick.builder().addCode("}\n");

                } else {
                    onClick.builder()
                           .addCode("if (id == $L) {\n  act." + me.e().getSimpleName() + "();\n}\n",
                                    id);
                }
                return false;
            }
        });

    }


    private ClassBuilder getClass(CE ce, String heroName) {

        if (Utils.containsClass(cs, heroName)) {
            return Utils.getClass(cs, heroName);
        } else {

            ClassBuilder heroClass = new ClassBuilder(heroName);
            heroClass.builder().addField(ce.className(), "act", Modifier.PRIVATE)
                     .superclass(Maker01ForHero.heroClass);
            //
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
            constructorBuilder.addParameter(ce.className(), "act");
            constructorBuilder.addCode("this.act=act;\n");
            heroClass.addConstructor(constructorBuilder);
            //
            MethodBuilder onClick = new MethodBuilder(METHOD_ON_CLICK);
            onClick.builder().addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
                   .addParameter(viewClass, "v").addStatement("int id = v.getId()");
            heroClass.addMethod(onClick);
            //
            MethodBuilder accept = new MethodBuilder(METHOD_ACCEPT);
            accept.builder().addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
                  .addParameter(stringClass, "code")
                  .addParameter(ParameterizedTypeName.get(resultClass, responseBodyClass),
                                "result");
            heroClass.addMethod(accept);
            //
            MethodBuilder setOnClick = new MethodBuilder(METHOD_SET_ON_CLICK);
            setOnClick.builder().addModifiers(Modifier.PRIVATE);
            setOnClick.builder().addParameter(TypeName.INT, "id");
            setOnClick.builder().addCode(
                    "try {\n  this.act.findViewById(id).setOnClickListener(this);\n} catch (Exception e) {\n  \n}\n");
            heroClass.addMethod(setOnClick);

            cs.add(heroClass);
            return heroClass;
        }
    }

}
