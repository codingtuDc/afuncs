package cn.yuanye1818.autils.net_utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

import cn.yuanye1818.autils.net_utils_annotation.BackType;
import cn.yuanye1818.autils.net_utils_annotation.NetBack;

public class NetMaker02ForHelper extends MyProcessor {

    private HashMap<String, ClassObject> classObjects = new HashMap<String, ClassObject>();
    private HashMap<String, HashMap<String, List<CodeObject>>> sbs = new HashMap<String, HashMap<String, List<CodeObject>>>();
    private HashMap<String, List<String>> codeNames = new HashMap<String, List<String>>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {


        dealNetBack(roundEnvironment);


        return true;
    }

    private void dealNetBack(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(NetBack.class);
        if (!isNull(elements)) {
            for (Element element : elements) {
                if (element instanceof ExecutableElement) {
                    ExecutableElement method = (ExecutableElement) element;

                    Element enclosingElement = method.getEnclosingElement();

                    if (enclosingElement instanceof TypeElement) {
                        TypeElement type = (TypeElement) enclosingElement;

                        ClassObject classObject = classObjects
                                .get(type.getQualifiedName().toString());

                        if (classObject == null) {
                            classObject = new ClassObject();
                            classObject.typeElement = type;
                            classObject.className = ClassName
                                    .bestGuess(type.getQualifiedName().toString());
                            classObjects.put(type.getQualifiedName().toString(), classObject);
                        }

                        String methodName = method.getSimpleName().toString();

                        List<String> names = codeNames
                                .get(classObject.typeElement.getQualifiedName().toString());

                        if (names == null) {
                            names = new ArrayList<String>();
                            codeNames.put(classObject.typeElement.getQualifiedName().toString(),
                                          names);
                        }

                        if (!names.contains(methodName)) {
                            names.add(methodName);
                        }


                        HashMap<String, List<CodeObject>> sbMap = sbs
                                .get(classObject.typeElement.getQualifiedName().toString());

                        if (sbMap == null) {
                            sbMap = new HashMap<String, List<CodeObject>>();
                            sbs.put(classObject.typeElement.getQualifiedName().toString(), sbMap);
                        }

                        List<CodeObject> codeObjects = sbMap.get(methodName);

                        if (codeObjects == null) {
                            codeObjects = new ArrayList<CodeObject>();
                            sbMap.put(methodName, codeObjects);

                            ArrayList ls = new ArrayList();
                            String code = "";
                            if (sbMap.keySet().size() <= 1) {
                                code = "if ($S.equals(code)) {";
                            } else {
                                code = "\n} else if ($S.equals(code)) {";
                            }
                            ls.add(methodName);
                            CodeObject codeObject = new CodeObject();
                            codeObject.code = code;
                            codeObject.params = ls.toArray();

                            codeObjects.add(codeObject);

                        }

                        NetBack annotation = method.getAnnotation(NetBack.class);
                        String back = null;
                        try {
                            Class backClass = annotation.value();
                        } catch (MirroredTypeException mte) {
                            TypeMirror classTypeMirror = mte.getTypeMirror();
                            TypeElement classTypeElement = (TypeElement) typeUtils
                                    .asElement(classTypeMirror);
                            back = classTypeElement.getQualifiedName().toString();
                        }
                        if (Void.class.getName().equals(back)) {
                            back = null;
                        }

                        if (back == null || back.trim().length() <= 0) {
                            CodeObject codeObject = new CodeObject();
                            codeObject.code = "\n  act.$N(result.error(), result.response());";
                            codeObject.params = new Object[]{methodName};
                            codeObjects.add(codeObject);
                        } else {

                            CodeObject co1 = new CodeObject();
                            CodeObject co2 = new CodeObject();
                            CodeObject co3 = new CodeObject();
                            List<? extends VariableElement> parameters = method.getParameters();

                            if (isNull(parameters)) {
                                co1.code = "\n  new $T() {";
                                co1.params = new Object[]{ClassName.bestGuess(back)};

                                co2.code = "\n      public void back() {";

                                co3.code = "\n          act.$N();";
                                co3.params = new Object[]{methodName};

                            } else {

                                String c1 = "";
                                ArrayList<Object> ob1 = new ArrayList<>();
                                String c2 = "";
                                ArrayList<Object> ob2 = new ArrayList<>();
                                String c3 = "";
                                ArrayList<Object> ob3 = new ArrayList<>();
                                ob3.add(methodName);
                                for (int i = 0; i < parameters.size(); i++) {

                                    VariableElement variableElement = parameters.get(i);

                                    BackType backTyp = variableElement
                                            .getAnnotation(BackType.class);

                                    if (i == 0) {
                                        c2 += "$T $N";
                                        c3 += "$N";
                                    } else {
                                        c2 += ",$T $N";
                                        c3 += ",$N";
                                    }

                                    if (backTyp != null) {
                                        if (ob1.size() == 0) {
                                            c1 += "$T";
                                        } else {
                                            c1 += ",$T";
                                        }
                                        ob1.add(ClassName.get(variableElement.asType()));
                                    }

                                    ob2.add(ClassName.get(variableElement.asType()));
                                    ob2.add(variableElement.getSimpleName().toString());
                                    ob3.add(variableElement.getSimpleName().toString());
                                }

                                if (ob1.size() > 0) {
                                    co1.code = "\n  new $T<" + c1 + ">() {";
                                } else {
                                    co1.code = "\n  new $T() {";
                                }
                                ob1.add(0, ClassName.bestGuess(back));
                                co1.params = ob1.toArray();

                                co2.code = "\n      public void back(" + c2 + ") {";
                                co2.params = ob2.toArray();

                                co3.code = "\n          act.$N(" + c3 + ");";
                                co3.params = ob3.toArray();

                            }

                            codeObjects.add(co1);
                            codeObjects.add(new CodeObject("\n      @Override"));
                            codeObjects.add(co2);
                            codeObjects.add(co3);
                            codeObjects.add(new CodeObject("\n      }"));
                            codeObjects.add(new CodeObject("\n  }.accept(code,result);"));


                        }


                    }

                }
            }
        }


        if (!isNull(classObjects.keySet())) {
            for (String key : classObjects.keySet()) {

                ClassObject classObject = classObjects.get(key);

                ClassName className = classObject.className;


                TypeSpec.Builder typeBuilder = TypeSpec
                        .classBuilder(className.simpleName() + "_Helper");
                typeBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);


                ClassName netHelperClass = ClassName
                        .bestGuess("cn.yuanye1818.autils.core.net.NetBackI");

                typeBuilder.addSuperinterface(netHelperClass);

                typeBuilder.addField(className, "act", Modifier.PRIVATE);

                MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
                constructorBuilder.addParameter(className, "act");
                constructorBuilder.addStatement("this.act = act");
                typeBuilder.addMethod(constructorBuilder.build());


                MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("accept");
                methodBuilder.addModifiers(Modifier.PUBLIC);
                methodBuilder.addAnnotation(Override.class);
                methodBuilder.addParameter(String.class, "code");

                ClassName resultClass = ClassName.bestGuess("retrofit2.adapter.rxjava2.Result");
                ClassName responseBodyClass = ClassName.bestGuess("okhttp3.ResponseBody");

                methodBuilder
                        .addParameter(ParameterizedTypeName.get(resultClass, responseBodyClass),
                                      "result");


                HashMap<String, List<CodeObject>> stringListHashMap = sbs
                        .get(classObject.typeElement.getQualifiedName().toString());


                List<String> names = codeNames
                        .get(classObject.typeElement.getQualifiedName().toString());

                for (int i = 0; i < names.size(); i++) {

                    String name = names.get(i);

                    List<CodeObject> codeObjects = stringListHashMap.get(name);

                    for (int j = 0; j < codeObjects.size(); j++) {
                        CodeObject codeObject = codeObjects.get(j);
                        if (codeObject.params == null || codeObject.params.length <= 0) {
                            methodBuilder.addCode(codeObject.code);
                        } else {
                            methodBuilder.addCode(codeObject.code, codeObject.params);
                        }
                    }

                }

                methodBuilder.addCode("\n}\n");


                typeBuilder.addMethod(methodBuilder.build());


                JavaFile netFile = JavaFile.builder(className.packageName(), typeBuilder.build())
                                           .build();

                try {
                    netFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public static class ClassObject {
        TypeElement typeElement;
        ClassName className;
    }

    public static class CodeObject {
        String code;
        Object[] params;

        public CodeObject(String code) {
            this.code = code;
        }

        public CodeObject() {

        }
    }

}
