package org.hope6537.note.design.template.example;

public abstract class AbstractClass {

    //钩子方法 有个默认情况
    protected boolean someAdvice() {
        return true;
    }

    //基本方法
    protected abstract void doSomething();

    //基本方法
    protected abstract void doAnything();

    /**
     * 模板方法，完成相关逻辑
     */
    public void templateMethod() {
        doAnything();
        if (someAdvice()) {
            System.out.println("其他逻辑");
        }
        doSomething();
    }


}

