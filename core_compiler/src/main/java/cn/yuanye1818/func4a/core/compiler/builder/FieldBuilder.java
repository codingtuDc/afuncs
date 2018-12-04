package cn.yuanye1818.func4a.core.compiler.builder;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;

import javax.lang.model.element.Modifier;

public class FieldBuilder {

    private String name;
    private FieldSpec.Builder builder;

    public FieldBuilder(TypeName typeName, String name) {
        this.name = name;
        this.builder = FieldSpec.builder(typeName, name);
    }

    public FieldBuilder(Type type, String name) {
        this.name = name;
        this.builder = FieldSpec.builder(type, name);
    }

    public FieldBuilder publicField() {
        builder.addModifiers(Modifier.PUBLIC);
        return this;
    }

    public FieldBuilder privateField() {
        builder.addModifiers(Modifier.PRIVATE);
        return this;
    }

    public FieldBuilder staticField() {
        builder.addModifiers(Modifier.STATIC);
        return this;
    }

    public FieldBuilder finalField() {
        builder.addModifiers(Modifier.FINAL);
        return this;
    }

    public FieldBuilder initializer(String format, Object... args) {
        builder.initializer(format, args);
        return this;
    }

    public FieldSpec build() {
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FieldBuilder that = (FieldBuilder) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public String getName() {
        return name;
    }
}
