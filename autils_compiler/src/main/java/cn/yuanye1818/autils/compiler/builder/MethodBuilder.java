package cn.yuanye1818.autils.compiler.builder;

import com.squareup.javapoet.MethodSpec;

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
}
