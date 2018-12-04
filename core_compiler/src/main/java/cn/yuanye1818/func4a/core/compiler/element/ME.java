package cn.yuanye1818.func4a.core.compiler.element;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class ME {

    private ExecutableElement e;
    private CE ce;
    private String name;

    public ME(ExecutableElement e) {
        this.e = e;
        ce = new CE((TypeElement) e.getEnclosingElement());
        this.name = e.getSimpleName().toString();
    }

    public CE ce() {
        return ce;
    }

    public ExecutableElement e() {
        return e;
    }

    public String name() {
        return name;
    }
}
