package cn.yuanye1818.autils.compiler.element;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.TypeElement;

public class CE {

    private String fullName;
    private TypeElement e;
    private ClassName className;

    public CE(TypeElement e) {
        this.e = e;
        this.fullName = e.getQualifiedName().toString();
        this.className = ClassName.bestGuess(this.fullName);
    }

    public String fullName() {
        return fullName;
    }

    public ClassName className() {
        return this.className;
    }

}
