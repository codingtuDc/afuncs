package cn.yuanye1818.func4a.core.compiler.element;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.VariableElement;

public class PE {

    private VariableElement e;
    private String typeName;
    private String name;

    public PE(VariableElement e) {
        this.e = e;
        this.typeName = ClassName.get(this.e.asType()).toString();
        this.name = e.getSimpleName().toString();
    }

    public VariableElement e() {
        return e;
    }

    public String typeName() {
        return typeName;
    }

    public String name() {
        return name;
    }

}
