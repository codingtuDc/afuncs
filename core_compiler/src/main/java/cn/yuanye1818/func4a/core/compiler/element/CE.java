package cn.yuanye1818.func4a.core.compiler.element;

import com.squareup.javapoet.ClassName;

import java.lang.annotation.Annotation;

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

    public String simpleName() {
        return this.className.simpleName();
    }

    public <A extends Annotation> A getAnnotation(Class<A> var1) {
        return this.e.getAnnotation(var1);
    }

}
