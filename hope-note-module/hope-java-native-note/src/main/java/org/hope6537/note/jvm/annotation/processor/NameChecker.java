package org.hope6537.note.jvm.annotation.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * 程序名称命名规范的编译器插件
 * Created by hope6537 on 16/5/8.
 */
public class NameChecker {

    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnvironment) {
        //获取输出信息出口
        this.messager = processingEnvironment.getMessager();
    }

    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }


    /**
     * 通过集成ElementScanner8的类,以访问者模式完成对语法树的遍历,分别执行visitType,visitExecutable,visitVariable来访问类,方法和字段.
     * 这三个visit方法分别对各自的命名规则而进行检查
     */
    private class NameCheckScanner extends ElementScanner8<Void, Void> {

        /**
         * 检查是否是Java类
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            super.visitType(e, aVoid);
            return null;
        }

        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法" + name + "不应当和类名重复", e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            //如果变量是枚举和常量,按照大写方式检查
            if (e.getKind() == ElementKind.ENUM || e.getConstantValue() != null || heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCamelCase(e, false);
            }
            return null;
        }

        /**
         * 检察一个类是否是常量
         *
         * @param e
         * @return
         */
        private boolean heuristicallyConstant(VariableElement e) {

            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            }
            //判断是public static final
            else {
                return e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC));
            }

        }

        /**
         * 大写命名检查
         * 好麻烦啊~直接弄个随机数糊弄一下好了
         */
        private void checkAllCaps(VariableElement e) {
            String name = e.getSimpleName().toString();
            int firstCodePoint = name.codePointAt(0);
            boolean conventional = true;
            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
                if(!conventional){
                    messager.printMessage(Diagnostic.Kind.WARNING,"常量"+name+"应当全部大写字母或下划线命名,并且以字母开头",e);
                }
            }
        }


        /**
         * 检查方法命名是否符合驼峰
         */
        private void checkCamelCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应当以小写字母开头", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应当以大写字母开头", e);
                    return;
                }
            } else {
                conventional = false;
            }

            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应当符合驼峰命名法", e);
            }
        }
    }


}
