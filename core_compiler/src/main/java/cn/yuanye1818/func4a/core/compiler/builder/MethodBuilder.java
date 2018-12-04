package cn.yuanye1818.func4a.core.compiler.builder;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.lang.reflect.Type;
import java.util.Map;

import javax.lang.model.element.Modifier;

public class MethodBuilder {
    private MethodSpec.Builder builder;
    private String name;

    public MethodBuilder(String name) {
        this.name = name;
        builder = MethodSpec.methodBuilder(this.name);
    }

    public MethodSpec.Builder builder() {
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MethodBuilder methodBuilder = (MethodBuilder) o;
        return name != null ? name.equals(methodBuilder.name) : methodBuilder.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public MethodBuilder publicMethod() {
        builder.addModifiers(Modifier.PUBLIC);
        return this;
    }

    public MethodBuilder privateMethod() {
        builder.addModifiers(Modifier.PRIVATE);
        return this;
    }

    public MethodBuilder staticMethod() {
        builder.addModifiers(Modifier.STATIC);
        return this;
    }

    public MethodBuilder finalMethod() {
        builder.addModifiers(Modifier.FINAL);
        return this;
    }

    public MethodBuilder abstractMethod() {
        builder.addModifiers(Modifier.ABSTRACT);
        return this;
    }

    public MethodBuilder protectedMethod() {
        builder.addModifiers(Modifier.PROTECTED);
        return this;
    }

    /**************************************************
     *
     *
     *
     **************************************************/

    public MethodBuilder addCode(String format, Object... args) {
        this.builder.addCode(format, args);
        return this;
    }

    public MethodBuilder addCodeLine(String format, Object... args) {
        this.builder.addCode(format + "\n", args);
        return this;
    }

    public MethodBuilder addParameter(TypeName type, String name, Modifier... modifiers) {
        this.builder.addParameter(type, name, modifiers);
        return this;
    }

    public MethodBuilder addParameter(Type type, String name, Modifier... modifiers) {
        this.builder.addParameter(type, name, modifiers);
        return this;
    }

    public MethodBuilder addJavadoc(String format, Object... args) {
        builder.addJavadoc(format, args);
        return this;
    }

    public MethodBuilder addJavadoc(CodeBlock block) {
        builder.addJavadoc(block);
        return this;
    }

    public MethodBuilder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
        builder.addAnnotations(annotationSpecs);
        return this;
    }

    public MethodBuilder addAnnotation(AnnotationSpec annotationSpec) {
        builder.addAnnotation(annotationSpec);
        return this;
    }

    public MethodBuilder addAnnotation(ClassName annotation) {
        builder.addAnnotation(annotation);
        return this;
    }

    public MethodBuilder addAnnotation(Class<?> annotation) {
        builder.addAnnotation(annotation);
        return this;
    }

    public MethodBuilder addModifiers(Modifier... modifiers) {
        builder.addModifiers(modifiers);
        return this;
    }

    public MethodBuilder addModifiers(Iterable<Modifier> modifiers) {
        builder.addModifiers(modifiers);
        return this;
    }

    public MethodBuilder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
        builder.addTypeVariables(typeVariables);
        return this;
    }

    public MethodBuilder addTypeVariable(TypeVariableName typeVariable) {
        builder.addTypeVariable(typeVariable);
        return this;
    }

    public MethodBuilder returns(TypeName returnType) {
        builder.returns(returnType);
        return this;
    }

    public MethodBuilder returns(Type returnType) {
        builder.returns(returnType);
        return this;
    }

    public MethodBuilder addParameters(Iterable<ParameterSpec> parameterSpecs) {
        builder.addParameters(parameterSpecs);
        return this;
    }

    public MethodBuilder addParameter(ParameterSpec parameterSpec) {
        builder.addParameter(parameterSpec);
        return this;
    }

    public MethodBuilder varargs() {
        builder.varargs();
        return this;
    }

    public MethodBuilder varargs(boolean varargs) {
        builder.varargs(varargs);
        return this;
    }

    public MethodBuilder addExceptions(Iterable<? extends TypeName> exceptions) {
        builder.addExceptions(exceptions);
        return this;
    }

    public MethodBuilder addException(TypeName exception) {
        builder.addException(exception);
        return this;
    }

    public MethodBuilder addException(Type exception) {
        builder.addException(exception);
        return this;
    }

    public MethodBuilder addNamedCode(String format, Map<String, ?> args) {
        builder.addNamedCode(format, args);
        return this;
    }

    public MethodBuilder addCode(CodeBlock codeBlock) {
        builder.addCode(codeBlock);
        return this;
    }

    public MethodBuilder addComment(String format, Object... args) {
        builder.addComment(format, args);
        return this;
    }

    public MethodBuilder defaultValue(String format, Object... args) {
        builder.defaultValue(format, args);
        return this;
    }

    public MethodBuilder defaultValue(CodeBlock codeBlock) {
        builder.defaultValue(codeBlock);
        return this;
    }

    public MethodBuilder beginControlFlow(String controlFlow, Object... args) {
        builder.beginControlFlow(controlFlow, args);
        return this;
    }

    public MethodBuilder nextControlFlow(String controlFlow, Object... args) {
        builder.nextControlFlow(controlFlow, args);
        return this;
    }

    public MethodBuilder endControlFlow() {
        builder.endControlFlow();
        return this;
    }

    public MethodBuilder endControlFlow(String controlFlow, Object... args) {
        builder.endControlFlow(controlFlow, args);
        return this;
    }

    public MethodBuilder addStatement(String format, Object... args) {
        builder.addStatement(format, args);
        return this;
    }

    public MethodBuilder addStatement(CodeBlock codeBlock) {
        builder.addStatement(codeBlock);
        return this;
    }

    public MethodSpec build() {
        return this.builder.build();
    }

}
