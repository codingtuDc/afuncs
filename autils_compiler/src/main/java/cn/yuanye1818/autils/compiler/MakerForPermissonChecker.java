package cn.yuanye1818.autils.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;

import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import cn.yuanye1818.autils.compiler.annotation.permission.PermissionCheck;
import cn.yuanye1818.autils.compiler.builder.ClassBuilder;
import cn.yuanye1818.autils.compiler.builder.MethodBuilder;
import cn.yuanye1818.autils.compiler.element.ME;
import cn.yuanye1818.autils.compiler.utils.ListUtils;
import cn.yuanye1818.autils.compiler.utils.Utils;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"cn.yuanye1818.autils.compiler.annotation.permission.PermissionCheck"})
public class MakerForPermissonChecker extends CoreMaker {

    ClassBuilder permissonChecker = null;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ListUtils.ls(roundEnvironment.getElementsAnnotatedWith(PermissionCheck.class),
                     new ListUtils.Each<Element>() {
                         @Override
                         public boolean each(int position, Element element) {
                             if (element instanceof ExecutableElement) {
                                 handlerMethodElement(position, (ExecutableElement) element);
                             }
                             return false;
                         }
                     });

        Utils.build(permissonChecker);

        return true;
    }

    private void handlerMethodElement(int position, ExecutableElement e) {
        ME me = new ME(e);
        if (permissonChecker == null) {
            permissonChecker = new ClassBuilder(CLASS_PERMISSONCHECKER);
        }

        String paramName = "helper";

        final MethodBuilder check = new MethodBuilder("check" + Utils.nameFirstUpper(me.name()));
        check.publicMethod().staticMethod();
        check.addParameter(permissionHelperClass, paramName);


        PermissionCheck annotation = me.e().getAnnotation(PermissionCheck.class);
        String[] value = annotation.value();

        String codeName = "CHECK_" + Utils.getStaticName(me.name());

        permissonChecker.builder().addField(FieldSpec.builder(int.class, codeName, Modifier.PUBLIC,
                                                              Modifier.STATIC, Modifier.FINAL)
                                                     .initializer("$L", position).build());

        if (annotation.isForce()) {
            check.addCodeLine("$T.check($N, $N,", permissionFuncClass, paramName, codeName);
        } else {
            String preferenceName = "CACHE_CHECK_" + Utils.getStaticName(me.name());
            permissonChecker.builder().addField(FieldSpec.builder(String.class, preferenceName,
                                                                  Modifier.PUBLIC, Modifier.STATIC,
                                                                  Modifier.FINAL).initializer("$S",
                                                                                              "permission_check_" + Utils
                                                                                                      .getStaticName(
                                                                                                              me.name())
                                                                                                      .toLowerCase())
                                                         .build());
            check.addCodeLine("$T.check($N, $N, $N,", permissionFuncClass, paramName, preferenceName, codeName);
        }

        check.addCode("   new String[]{");

        ListUtils.ls(value, new ListUtils.Each<String>() {
            @Override
            public boolean each(int position, String s) {

                if (position == 0) {
                    check.addCode("$S", s);
                } else {
                    check.addCode(", $S", s);
                }
                return false;
            }
        });


        check.addCodeLine("});");

        permissonChecker.addMethod(check);


    }

}
