package org.hope6537.note.jvm.methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * MethodHandle示例代码
 * Created by hope6537 on 16/5/8.
 */
public class MethodHandleExample {

    static class ClassA {
        public void println(String s) {
            System.out.println("using method");
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        getPrintlnMethodHandle(obj).invokeExact("hope6537");
    }

    /**
     * @param receiver 接收者
     * @return
     */
    private static MethodHandle getPrintlnMethodHandle(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        //定义方法类型,返回值和参数类型
        MethodType methodType = MethodType.methodType(void.class, String.class);
        //在指定类(receiver.getClass()中查找符合给定的方法名称,方法类型,调用权限的方法句柄)
        //在这里调用虚方法(virtual),根据Java语言的规则,方法的第一个参数是隐式的,就是对象的实例,俗称this,可以通过bindTo方法来讲this绑定到receiver上
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
    }


}


