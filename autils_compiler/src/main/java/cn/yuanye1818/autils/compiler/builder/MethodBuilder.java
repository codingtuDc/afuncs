package cn.yuanye1818.autils.compiler.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;

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

    public void addCode(String format, Object... args) {
        this.builder.addCode(format, args);
    }

    public void addCodeLine(String format, Object... args) {
        this.builder.addCode(format + "\n", args);
    }

    public MethodBuilder addParameter(TypeName type, String name, Modifier... modifiers) {
        this.builder.addParameter(type, name, modifiers);
        return this;
    }

    public MethodSpec.Builder addParameter(Type type, String name, Modifier... modifiers) {
        this.builder.addParameter(type, name, modifiers);
        return this.builder;
    }

    public MethodSpec build() {
        return this.builder.build();
    }
}
