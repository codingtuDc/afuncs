package cn.yuanye1818.autils.net_utils;

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

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import cn.yuanye1818.autils.net_utils_annotation.Api;
import cn.yuanye1818.autils.net_utils_annotation.BaseUrl;

public class NetMaker01ForNet extends MyProcessor {

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


        JavaFile netFile = JavaFile.builder("cn.yuanye1818.autils.net", net.build()).build();

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

        String staticName = getStaticName(methodName);

        defaultCodeFieldSpecs.add(FieldSpec.builder(String.class, staticName, Modifier.PUBLIC,
                                                    Modifier.STATIC, Modifier.FINAL)
                                           .initializer("\"" + methodName + "\"").build());

        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);


        List<? extends VariableElement> parameters = methodElement.getParameters();


        ClassName classApi = ClassName.bestGuess("cn.yuanye1818.autils.core.net.Api");
        ClassName classService = ClassName.bestGuess(api.getQualifiedName().toString());
        ClassName classNetUtils = ClassName.bestGuess("cn.yuanye1818.autils.core.net.NetUtils");

        builder.returns(classApi);

        ArrayList ps = new ArrayList();
        ps.add(classNetUtils);
        ps.add(classNetUtils);
        ps.add(classService);

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

            url += ",$N";
            ps.add(valueName);
        }

        ps.add(methodName);

        StringBuilder sb = new StringBuilder();
        if (!isNull(parameters)) {

            for (int i = 0; i < parameters.size(); i++) {

                VariableElement methodParameter = parameters.get(i);

                TypeMirror methodParameterType = methodParameter.asType();

                TypeName typeName = TypeName.get(methodParameterType);

                String paramName = methodParameter.getSimpleName().toString();

                builder.addParameter(typeName, paramName);

                ps.add(paramName);

                if (i == 0) {
                    sb.append("$N");
                } else {
                    sb.append(",$N");
                }

            }
        }

        ps.add(staticName);


        builder.addStatement(
                "return $T.api($T.create($T.class" + url + ").$N(" + sb.toString() + "),$N)",
                ps.toArray());

        methodSpecs.add(builder.build());

    }

    private String getStaticName(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 97 && c <= 122) {
                sb.append((char) (c - 32));
            } else if (c >= 65 && c <= 90) {
                sb.append("_" + c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    //    private void findAndParseListener(RoundEnvironment env,
    //            Class<? extends Annotation> annotationClass,
    //            Map<TypeElement, BindingSet.Builder> builderMap, Set<TypeElement> erasedTargetNames) {
    //        for (Element element : env.getElementsAnnotatedWith(annotationClass)) {
    //            if (!SuperficialValidation.validateElement(element))
    //                continue;
    //            try {
    //                parseListenerAnnotation(annotationClass, element, builderMap, erasedTargetNames);
    //            } catch (Exception e) {
    //                StringWriter stackTrace = new StringWriter();
    //                e.printStackTrace(new PrintWriter(stackTrace));
    //
    //                error(element, "Unable to generate view binder for @%s.\n\n%s",
    //                      annotationClass.getSimpleName(), stackTrace.toString());
    //            }
    //        }
    //    }
    //
    //    private void parseListenerAnnotation(Class<? extends Annotation> annotationClass,
    //            Element element, Map<TypeElement, BindingSet.Builder> builderMap,
    //            Set<TypeElement> erasedTargetNames) throws Exception {
    //        // This should be guarded by the annotation's @Target but it's worth a check for safe casting.
    //        if (!(element instanceof ExecutableElement) || element.getKind() != METHOD) {
    //            throw new IllegalStateException(String.format("@%s annotation must be on a method.",
    //                                                          annotationClass.getSimpleName()));
    //        }
    //
    //        ExecutableElement executableElement = (ExecutableElement) element;
    //        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
    //
    //        // Assemble information on the method.
    //        Annotation annotation = element.getAnnotation(annotationClass);
    //        Method annotationValue = annotationClass.getDeclaredMethod("value");
    //        if (annotationValue.getReturnType() != int[].class) {
    //            throw new IllegalStateException(
    //                    String.format("@%s annotation value() type not int[].", annotationClass));
    //        }
    //
    //        int[] ids = (int[]) annotationValue.invoke(annotation);
    //        String name = executableElement.getSimpleName().toString();
    //        boolean required = isListenerRequired(executableElement);
    //
    //        // Verify that the method and its containing class are accessible via generated code.
    //        boolean hasError = isInaccessibleViaGeneratedCode(annotationClass, "methods", element);
    //        hasError |= isBindingInWrongPackage(annotationClass, element);
    //
    //        Integer duplicateId = findDuplicate(ids);
    //        if (duplicateId != null) {
    //            error(element, "@%s annotation for method contains duplicate ID %d. (%s.%s)",
    //                  annotationClass.getSimpleName(), duplicateId, enclosingElement.getQualifiedName(),
    //                  element.getSimpleName());
    //            hasError = true;
    //        }
    //
    //        ListenerClass listener = annotationClass.getAnnotation(ListenerClass.class);
    //        if (listener == null) {
    //            throw new IllegalStateException(
    //                    String.format("No @%s defined on @%s.", ListenerClass.class.getSimpleName(),
    //                                  annotationClass.getSimpleName()));
    //        }
    //
    //        for (int id : ids) {
    //            if (id == NO_ID.value) {
    //                if (ids.length == 1) {
    //                    if (!required) {
    //                        error(element,
    //                              "ID-free binding must not be annotated with @Optional. (%s.%s)",
    //                              enclosingElement.getQualifiedName(), element.getSimpleName());
    //                        hasError = true;
    //                    }
    //                } else {
    //                    error(element, "@%s annotation contains invalid ID %d. (%s.%s)",
    //                          annotationClass.getSimpleName(), id, enclosingElement.getQualifiedName(),
    //                          element.getSimpleName());
    //                    hasError = true;
    //                }
    //            }
    //        }
    //
    //        ListenerMethod method;
    //        ListenerMethod[] methods = listener.method();
    //        if (methods.length > 1) {
    //            throw new IllegalStateException(
    //                    String.format("Multiple listener methods specified on @%s.",
    //                                  annotationClass.getSimpleName()));
    //        } else if (methods.length == 1) {
    //            if (listener.callbacks() != ListenerClass.NONE.class) {
    //                throw new IllegalStateException(
    //                        String.format("Both method() and callback() defined on @%s.",
    //                                      annotationClass.getSimpleName()));
    //            }
    //            method = methods[0];
    //        } else {
    //            Method annotationCallback = annotationClass.getDeclaredMethod("callback");
    //            Enum<?> callback = (Enum<?>) annotationCallback.invoke(annotation);
    //            Field callbackField = callback.getDeclaringClass().getField(callback.name());
    //            method = callbackField.getAnnotation(ListenerMethod.class);
    //            if (method == null) {
    //                throw new IllegalStateException(String.format("No @%s defined on @%s's %s.%s.",
    //                                                              ListenerMethod.class.getSimpleName(),
    //                                                              annotationClass.getSimpleName(),
    //                                                              callback.getDeclaringClass()
    //                                                                      .getSimpleName(),
    //                                                              callback.name()));
    //            }
    //        }
    //
    //        // Verify that the method has equal to or less than the number of parameters as the listener.
    //        List<? extends VariableElement> methodParameters = executableElement.getParameters();
    //        if (methodParameters.size() > method.parameters().length) {
    //            error(element, "@%s methods can have at most %s parameter(s). (%s.%s)",
    //                  annotationClass.getSimpleName(), method.parameters().length,
    //                  enclosingElement.getQualifiedName(), element.getSimpleName());
    //            hasError = true;
    //        }
    //
    //        // Verify method return type matches the listener.
    //        TypeMirror returnType = executableElement.getReturnType();
    //        if (returnType instanceof TypeVariable) {
    //            TypeVariable typeVariable = (TypeVariable) returnType;
    //            returnType = typeVariable.getUpperBound();
    //        }
    //        if (!returnType.toString().equals(method.returnType())) {
    //            error(element, "@%s methods must have a '%s' return type. (%s.%s)",
    //                  annotationClass.getSimpleName(), method.returnType(),
    //                  enclosingElement.getQualifiedName(), element.getSimpleName());
    //            hasError = true;
    //        }
    //
    //        if (hasError) {
    //            return;
    //        }
    //
    //        Parameter[] parameters = Parameter.NONE;
    //        if (!methodParameters.isEmpty()) {
    //            parameters = new Parameter[methodParameters.size()];
    //            BitSet methodParameterUsed = new BitSet(methodParameters.size());
    //            String[] parameterTypes = method.parameters();
    //            for (int i = 0; i < methodParameters.size(); i++) {
    //                VariableElement methodParameter = methodParameters.get(i);
    //                TypeMirror methodParameterType = methodParameter.asType();
    //                if (methodParameterType instanceof TypeVariable) {
    //                    TypeVariable typeVariable = (TypeVariable) methodParameterType;
    //                    methodParameterType = typeVariable.getUpperBound();
    //                }
    //
    //                for (int j = 0; j < parameterTypes.length; j++) {
    //                    if (methodParameterUsed.get(j)) {
    //                        continue;
    //                    }
    //                    if ((isSubtypeOfType(methodParameterType, parameterTypes[j]) && isSubtypeOfType(
    //                            methodParameterType, VIEW_TYPE)) || isTypeEqual(methodParameterType,
    //                                                                            parameterTypes[j]) || isInterface(
    //                            methodParameterType)) {
    //                        parameters[i] = new Parameter(j, TypeName.get(methodParameterType));
    //                        methodParameterUsed.set(j);
    //                        break;
    //                    }
    //                }
    //                if (parameters[i] == null) {
    //                    StringBuilder builder = new StringBuilder();
    //                    builder.append("Unable to match @").append(annotationClass.getSimpleName())
    //                           .append(" method arguments. (")
    //                           .append(enclosingElement.getQualifiedName()).append('.')
    //                           .append(element.getSimpleName()).append(')');
    //                    for (int j = 0; j < parameters.length; j++) {
    //                        Parameter parameter = parameters[j];
    //                        builder.append("\n\n  Parameter #").append(j + 1).append(": ")
    //                               .append(methodParameters.get(j).asType().toString())
    //                               .append("\n    ");
    //                        if (parameter == null) {
    //                            builder.append("did not match any listener parameters");
    //                        } else {
    //                            builder.append("matched listener parameter #")
    //                                   .append(parameter.getListenerPosition() + 1).append(": ")
    //                                   .append(parameter.getType());
    //                        }
    //                    }
    //                    builder.append("\n\nMethods may have up to ").append(method.parameters().length)
    //                           .append(" parameter(s):\n");
    //                    for (String parameterType : method.parameters()) {
    //                        builder.append("\n  ").append(parameterType);
    //                    }
    //                    builder.append(
    //                            "\n\nThese may be listed in any order but will be searched for from top to bottom.");
    //                    error(executableElement, builder.toString());
    //                    return;
    //                }
    //            }
    //        }
    //
    //        MethodViewBinding binding = new MethodViewBinding(name, Arrays.asList(parameters),
    //                                                          required);
    //        BindingSet.Builder builder = getOrCreateBindingBuilder(builderMap, enclosingElement);
    //        Map<Integer, Id> resourceIds = elementToIds(element, annotationClass, ids);
    //
    //        for (Map.Entry<Integer, Id> entry : resourceIds.entrySet()) {
    //            if (!builder.addMethod(entry.getValue(), listener, method, binding)) {
    //                error(element,
    //                      "Multiple listener methods with return value specified for ID %d. (%s.%s)",
    //                      entry.getKey(), enclosingElement.getQualifiedName(), element.getSimpleName());
    //                return;
    //            }
    //        }
    //
    //        // Add the type-erased version to the valid binding targets set.
    //        erasedTargetNames.add(enclosingElement);
    //    }

}
