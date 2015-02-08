package org.hope6537.note.thinking_in_java.fourteen;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 一个接口，用于当做基类来操作
 * @signdate 2014-7-17下午01:59:41
 * @company Changchun University&SHXT
 */
interface Interface {
    void doSomething();

    void somethingElse(String arg);
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 实体类，真正实现业务的类
 * @signdate 2014-7-17下午02:00:08
 * @company Changchun University&SHXT
 */
class RealObject implements Interface {
    @Override
    public void doSomething() {
        System.out.println("Real Doing");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("Here Something Else " + arg);
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 静态代理类，用于实现额外的不同的操作，例如追踪
 * @signdate 2014-7-17下午02:00:23
 * @company Changchun University&SHXT
 */
class SimpleProxy implements Interface {
    private Interface proxy;

    public SimpleProxy(Interface inter) {
        this.proxy = inter;
    }

    @Override
    public void doSomething() {
        System.out.println("Proxying");
        proxy.doSomething();
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("Proxying Else");
        proxy.somethingElse(arg);
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 主方法类
 * @signdate 2014-7-17下午02:01:23
 * @company Changchun University&SHXT
 */
class ProxyDemo {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("Classic");
    }

    public static void main(String[] args) {
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 动态代理类
 * @signdate 2014-7-17下午02:01:31
 * @company Changchun University&SHXT
 */
class DynamicProxyHander implements InvocationHandler {
    private Object proxyed;

    public DynamicProxyHander(Object object) {
        proxyed = object;
    }

    /*
     * (non-Javadoc) 实现代理的反射方法
     *
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
     * java.lang.reflect.Method, java.lang.Object[])
     *
     * @author:Hope6537(赵鹏)
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        // 获取到类，方法和参数
        System.out.println("----Proxying : " + proxy.getClass() + " , Method: "
                + method + " ,args: " + args);
        // 如果有参数进行输出
        if (args != null) {
            for (Object arg : args) {
                System.out.println("--:" + arg);
            }
        }
        // 最后返回方法并调用
        return method.invoke(proxyed, args);

    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 方法选择器
 * @signdate 2014-7-17下午02:17:52
 * @company Changchun University&SHXT
 */
class MethodSelector implements InvocationHandler {

    private Object proxyed;
    private String wonder;

    public MethodSelector(Object object, String wonder) {
        this.wonder = wonder;
        proxyed = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //选择和wonder相同的方法名称的方法，进行操作
        if (method.getName().equals(wonder)) {
            System.out.println("Confirm");
        }
        return method.invoke(proxyed, args);
    }

}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 实现类，动态代理
 * @signdate 2014-7-17下午02:03:16
 * @company Changchun University&SHXT
 */
class SimpleDynamicProxy {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("Dynamic");

    }

    public static void main(String[] args) {
        RealObject realobject = new RealObject();
        consumer(realobject);
        /*Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class
				.getClassLoader(), new Class[] { Interface.class },
				new DynamicProxyHander(realobject));*/
        Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class
                        .getClassLoader(), new Class[]{Interface.class},
                new MethodSelector(realobject, "doSomething"));
        consumer(proxy);
    }
}

public class ProxyTest {
    public static void main(String[] args) {
        ProxyDemo.main(args);
        SimpleDynamicProxy.main(args);
    }
}
