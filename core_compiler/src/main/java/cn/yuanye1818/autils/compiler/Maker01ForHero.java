package cn.yuanye1818.autils.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

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
import javax.lang.model.element.VariableElement;

import cn.yuanye1818.autils.compiler.annotation.net.NetBack;
import cn.yuanye1818.autils.compiler.annotation.onactivityresult.OnResult;
import cn.yuanye1818.autils.compiler.annotation.onclick.ClickTag;
import cn.yuanye1818.autils.compiler.annotation.onclick.ClickView;
import cn.yuanye1818.autils.compiler.annotation.permission.PermissionCheck;
import cn.yuanye1818.autils.compiler.annotation.view.FindView;
import cn.yuanye1818.autils.compiler.builder.ClassBuilder;
import cn.yuanye1818.autils.compiler.builder.MethodBuilder;
import cn.yuanye1818.autils.compiler.element.CE;
import cn.yuanye1818.autils.compiler.element.ME;
import cn.yuanye1818.autils.compiler.element.PE;
import cn.yuanye1818.autils.compiler.utils.Utils;
import cn.yuanye1818.func4j.CountFunc;
import cn.yuanye1818.func4j.StringFunc;
import cn.yuanye1818.func4j.ls.each.Each;

import static cn.yuanye1818.func4j.ls.Ls.ls;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"cn.yuanye1818.autils.compiler.annotation.onclick.ClickView"})
public class Maker01ForHero extends CoreMaker {

    public static final String METHOD_SET_ON_CLICK = "setOnClick";
    public static final String METHOD_GET_ACT = "getAct";
    public static final String METHOD_ON_PERMISSIONS_BACK = "onPermissionsBack";
    public static final String METHOD_ON_ACTIVITY_RESULT = "onActivityResult";
    public static final String METHOD_ON_CLICK = "onClick";
    public static final String METHOD_ACCEPT = "accept";

    public static final String BINDER_NAME = "binder";
    public static final String VIEW_NAME = "view";

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
        ls(roundEnvironment.getElementsAnnotatedWith(PermissionCheck.class), new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealPermissionCheckElement((ExecutableElement) element);
                }
                return false;
            }
        });
        ls(roundEnvironment.getElementsAnnotatedWith(OnResult.class), new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealOnResultElement((ExecutableElement) element);
                }
                return false;
            }
        });

        ls(cs, new Each<ClassBuilder>() {
            @Override
            public boolean each(int position, final ClassBuilder classBuilder) {
                Utils.build(classBuilder);
                return false;
            }
        });

        return true;
    }

    private void dealOnResultElement(ExecutableElement e) {
        ME me = new ME(e);
        CE ce = me.ce();
        ClassBuilder heroClass = getClass(ce, Utils.getHeroName(ce));

        final MethodBuilder onActivityResult = heroClass.getMethod(METHOD_ON_ACTIVITY_RESULT);

        if (me.e().getModifiers().contains(Modifier.PRIVATE)) {
            throw new RuntimeException("回调方法 " + me.name() + " 必须为公有方法");
        }

        OnResult annotation = me.e().getAnnotation(OnResult.class);
        int value = annotation.value();

        //if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.STORY) {
        //            binder.storyBack(data);
        //        }
        onActivityResult.addCodeLine("  if (resultCode == $T.RESULT_OK && requestCode == $L) {",
                                     activityClass, value);
        onActivityResult.addCodeLine("    binder.$N(data);", me.name());
        onActivityResult.addCodeLine("  }");

    }

    private void dealPermissionCheckElement(ExecutableElement e) {
        ME me = new ME(e);
        CE ce = me.ce();
        ClassBuilder heroClass = getClass(ce, Utils.getHeroName(ce));

        final MethodBuilder onPermissionBack = heroClass.getMethod(METHOD_ON_PERMISSIONS_BACK);

        if (me.e().getModifiers().contains(Modifier.PRIVATE)) {
            throw new RuntimeException("回调方法 " + me.name() + " 必须为公有方法");
        }

        PermissionCheck annotation = me.e().getAnnotation(PermissionCheck.class);

        if (annotation.isForce()) {
            onPermissionBack
                    .addCodeLine("  if (requestCode == $T.CHECK_$N) {", permissionCheckerClass,
                                 StringFunc.getStaticName(me.name()));
            onPermissionBack.addCodeLine("    if ($T.allow(grantResults)) {", permissionFuncClass);
            onPermissionBack.addCodeLine("      binder.$N();", me.name());
            onPermissionBack.addCodeLine("    } else {");
            onPermissionBack.addCodeLine("      $T.toast(\"您禁用了相关权限，无法完成操作\");", toastFuncClass);
            onPermissionBack.addCodeLine("    }");
            onPermissionBack.addCodeLine("  }");
        } else {
            onPermissionBack
                    .addCodeLine("  if (requestCode == $T.CHECK_$N) {", permissionCheckerClass,
                                 StringFunc.getStaticName(me.name()));
            onPermissionBack.addCodeLine("    try {");
            onPermissionBack.addCodeLine("      binder.$N();", me.name());
            onPermissionBack.addCodeLine("    } catch (Exception e) {");
            onPermissionBack.addCodeLine("      $T.w(e);", logsClass);
            onPermissionBack.addCodeLine("    }");
            onPermissionBack.addCodeLine("  }");
        }

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
                "this.$N." + pe.name() + " = $N.findViewById(" + annotation.value() + ");\n",
                BINDER_NAME, VIEW_NAME);

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
                  .addCode("if ($S.equals(code)) {\n  this.$N.$N(", me.name(), BINDER_NAME,
                           me.name());
            ls(params, new Each<VariableElement>() {
                @Override
                public boolean each(int position, VariableElement e) {

                    if (position != 0)
                        accept.builder().addCode(", ");

                    PE pe = new PE(e);
                    if (Throwable.class.getName().equals(pe.typeName())) {
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

            accept.builder().addCode("() {\n    @$T\n    public void back(", Override.class);

            Utils.addMethodParamDefineCode(accept, params);

            accept.builder().addCode(") {\n      $N.$N(", BINDER_NAME, me.name());

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

                heroClass.constructorBuilder()
                         .addCode(METHOD_SET_ON_CLICK + "($N," + id + ");\n", VIEW_NAME);

                List<? extends VariableElement> parameters = me.e().getParameters();

                if (CountFunc.count(parameters) > 0) {

                    onClick.builder().addCode("if (id == $L) {\n", id);

                    onClick.builder().addCode("  $N.$N(", BINDER_NAME, me.e().getSimpleName());

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
                    onClick.builder().addCode("if (id == $L) {\n  $N.$N();\n}\n", id, BINDER_NAME,
                                              me.e().getSimpleName());
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
            heroClass.builder().addField(ce.className(), BINDER_NAME, Modifier.PRIVATE)
                     .addSuperinterface(Maker01ForHero.heroClass);
            //
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
            constructorBuilder.addParameter(ce.className(), BINDER_NAME);
            constructorBuilder.addParameter(viewClass, VIEW_NAME);
            constructorBuilder.addCode("this.$N=$N;\n", BINDER_NAME, BINDER_NAME);
            heroClass.addConstructor(constructorBuilder);
            //
            MethodBuilder onClick = new MethodBuilder(METHOD_ON_CLICK);
            onClick.builder().addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
                   .addParameter(viewClass, "v").addStatement("int id = v.getId()");
            heroClass.addMethod(onClick);
            //
            MethodBuilder accept = new MethodBuilder(METHOD_ACCEPT);
            accept.builder().addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
                  .addParameter(String.class, "code")
                  .addParameter(ParameterizedTypeName.get(resultClass, responseBodyClass),
                                "result");
            heroClass.addMethod(accept);
            //
            MethodBuilder setOnClick = new MethodBuilder(METHOD_SET_ON_CLICK);
            setOnClick.builder().addModifiers(Modifier.PRIVATE);
            setOnClick.builder().addParameter(viewClass, VIEW_NAME);
            setOnClick.builder().addParameter(TypeName.INT, "id");
            setOnClick.builder().addCode(
                    "try {\n  $N.findViewById(id).setOnClickListener(this);\n} catch (Exception e) {\n  \n}\n",
                    VIEW_NAME);
            heroClass.addMethod(setOnClick);
            //
            MethodBuilder getAct = new MethodBuilder(METHOD_GET_ACT);
            getAct.publicMethod();
            getAct.builder().returns(activityClass);
            getAct.builder().addAnnotation(Override.class);
            getAct.addCodeLine("  return binder instanceof $T ? binder : null;", activityClass);
            heroClass.addMethod(getAct);
            //
            MethodBuilder onPermissionBack = new MethodBuilder(METHOD_ON_PERMISSIONS_BACK);
            onPermissionBack.builder().addAnnotation(Override.class);
            onPermissionBack.publicMethod();
            onPermissionBack.addParameter(int.class, "requestCode");
            onPermissionBack.addParameter(String[].class, "permissions");
            onPermissionBack.addParameter(int[].class, "grantResults");
            heroClass.addMethod(onPermissionBack);
            //
            MethodBuilder onActivityResult = new MethodBuilder(METHOD_ON_ACTIVITY_RESULT);
            onActivityResult.builder().addAnnotation(Override.class);
            onActivityResult.publicMethod();
            onActivityResult.addParameter(int.class, "requestCode");
            onActivityResult.addParameter(int.class, "resultCode");
            onActivityResult.addParameter(intentClass, "data");
            heroClass.addMethod(onActivityResult);

            cs.add(heroClass);
            return heroClass;
        }
    }

}
