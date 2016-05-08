package org.hope6537.note.jvm.invokedynamic;

import java.lang.invoke.*;

/**
 * InvokeDynamic接入点
 * Created by hope6537 on 16/5/8.
 */
public class InvokeDynamicExample {

    public static void main(String[] args) throws Throwable {
        //根据CallSite的信息,调用目标方法
        INDY_BootstrapMethod().invokeExact("hope6537");
    }

    public static void testMethod(String s) {
        System.out.println("print " + s);
    }

    /**
     * 根据引导方法.得到一个CallSite对象
     *
     * @param lookup     MethodHandles.Lookup
     * @param name       方法名称
     * @param methodType 方法描述符
     * @return 返回CallSite信息, 靠他找到真正要执行的目标方法调用
     * @throws Throwable
     */
    private static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType methodType) throws Throwable {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicExample.class, name, methodType));
    }

    /**
     * @return 返回引导方法的名称和方法Indify.java类型
     * @throws Throwable
     */
    private static MethodHandle MH_BootstrapMethod() throws Throwable {
        return MethodHandles.lookup().findStatic(InvokeDynamicExample.class, "BootstrapMethod", MT_BootstrapMethod());
    }

    /**
     * @return 定义的引导方法描述符
     */
    private static MethodType MT_BootstrapMethod() {
        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
    }

    /**
     * @return 调用引导方法, 并生成CallSite->真正要执行的目标方法调用
     * @throws Throwable
     */
    private static MethodHandle INDY_BootstrapMethod() throws Throwable {
        CallSite testMethod = (CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod", MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
        return testMethod.dynamicInvoker();
    }


}
