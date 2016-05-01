package org.hope6537.note.thinking_in_java.fifteen;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

class MixinProxy implements InvocationHandler {
    /**
     * @Describe 遗世而独立 碉堡的 方法<->对象关系映射
     */
    Map<String, Object> delehatesByMethod;

    /**
     * @param @param pairs
     * @Describe 构造方法创建动态代理的Class的方法体 放入映射中，以待查找
     * 一个方法对应这一个类对象，Object而不是字符串，是可以执行的对象（Class）
     * @Author Hope6537(赵鹏)
     */
    public MixinProxy(TwoTuple<Object, Class<?>>... pairs) {
        delehatesByMethod = new HashMap<String, Object>();
        for (TwoTuple<Object, Class<?>> pair : pairs) {
            for (Method method : pair.second.getMethods()) {
                String methodName = method.getName();
                if (!delehatesByMethod.containsKey(methodName)) {
                    delehatesByMethod.put(methodName, pair.first);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // 未定义泛型，所以狂出警告
    public static Object newInstance(TwoTuple... pairs) {
        Class[] interfaces = new Class[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            interfaces[i] = (Class) pairs[i].second;
        }
        // 卧槽这句啥意思,类装载器？
        ClassLoader c1 = pairs[0].first.getClass().getClassLoader();
        // 返回的Object是由这东西生成的？
        return Proxy.newProxyInstance(c1, interfaces, new MixinProxy(pairs));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
     * java.lang.reflect.Method, java.lang.Object[])
     *
     * @Author:Hope6537(赵鹏) 执行反射方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String methodName = method.getName();
        Object delegate = delehatesByMethod.get(methodName);
        return method.invoke(delegate, args);
    }

}

/**
 * @version 0.9
 * @Describe 动态代理产生混型
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-20下午01:57:21
 * @company Changchun University&SHXT
 */
public class DynamicProxyMinin {
    public static void main(String[] args) {
        Object mixin = MixinProxy.newInstance(
                tuple(new BasicImp(), Basic.class), tuple(new TimeStampedImp(),
                        TimeStamped.class), tuple(new SerialNumberImp(),
                        SerialNumbered.class));

        Basic b = (Basic) mixin;
        TimeStamped t = (TimeStamped) mixin;
        SerialNumbered s = (SerialNumbered) mixin;
        b.set("Hello");
        System.out.println(b.get());
        System.out.println(t.getStamp());
        System.out.println(s.getSerialNumber());
    }

    /**
     * @Descirbe 生成该类型的持有对象，分别装载Object对象和Class对象
     * @Author Hope6537(赵鹏)
     * @Params @param object
     * @Params @param class1
     * @Params @return
     * @SignDate 2014-7-20下午02:05:36
     * @Version 0.9
     */
    @SuppressWarnings("unchecked")
    private static TwoTuple tuple(Object object, Class class1) {
        return new TwoTuple<Object, Class>(object, class1);
    }
}
