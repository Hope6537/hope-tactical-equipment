package org.hope6537.note.design.proxy.dymatic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DymaticGamePlay implements InvocationHandler {

    Class<?> cls = null;

    Object object = null;

    public DymaticGamePlay(Object object) {
        super();
        cls = object.getClass();
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = method.invoke(proxy, args);
        return result;
    }

}
