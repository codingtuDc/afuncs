package cn.yuanye1818.func4a.core.compiler.builder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import cn.yuanye1818.func4j.ls.Ls;
import cn.yuanye1818.func4j.ls.each.Each;

import static cn.yuanye1818.func4j.ls.Ls.ls;

public class ClassBuilder {
    private ClassName className;
    private String fullName;
    private TypeSpec.Builder builder;
    private List<MethodBuilder> methodBuilders;
    private List<FieldBuilder> fieldBuilders;
    private MethodSpec.Builder constructorBuilder;


    public ClassBuilder(String fullName) {
        this.fullName = fullName;
        this.className = ClassName.bestGuess(this.fullName);
        builder = TypeSpec.classBuilder(this.className.simpleName());
        builder.addModifiers(Modifier.PUBLIC);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClassBuilder classBuilder = (ClassBuilder) o;

        return fullName != null ? fullName
                .equals(classBuilder.fullName) : classBuilder.fullName == null;
    }

    @Override
    public int hashCode() {
        return fullName != null ? fullName.hashCode() : 0;
    }

    public TypeSpec.Builder builder() {
        return builder;
    }

    public String getPackgeName() {
        return this.className.packageName();
    }

    public TypeSpec build() {

        Ls.ls(fieldBuilders, (position, f) -> {
            builder.addField(f.build());
            return false;
        });


        ls(methodBuilders, new Each<MethodBuilder>() {
            @Override
            public boolean each(int position, MethodBuilder b) {
                builder.addMethod(b.build());
                return false;
            }
        });

        if (constructorBuilder != null)
            builder.addMethod(constructorBuilder.build());

        return builder.build();
    }

    public void addMethod(MethodBuilder methodBuilder) {
        if (this.methodBuilders == null)
            this.methodBuilders = new ArrayList<MethodBuilder>();
        this.methodBuilders.add(methodBuilder);
    }

    public List<MethodBuilder> getMethodBuilders() {
        return methodBuilders;
    }

    public MethodBuilder getMethod(String name) {
        return this.methodBuilders.get(this.methodBuilders.indexOf(new MethodBuilder(name)));
    }

    public void addConstructor(MethodSpec.Builder constructorBuilder) {
        this.constructorBuilder = constructorBuilder;
    }

    public MethodSpec.Builder constructorBuilder() {
        return constructorBuilder;
    }

    public ClassBuilder addField(FieldBuilder builder) {
        if (this.fieldBuilders == null)
            this.fieldBuilders = new ArrayList<FieldBuilder>();

        if (!this.fieldBuilders.contains(builder)) {
            this.fieldBuilders.add(builder);
        }
        return this;
    }

    public List<FieldBuilder> getFieldBuilders() {
        return fieldBuilders;
    }
}
