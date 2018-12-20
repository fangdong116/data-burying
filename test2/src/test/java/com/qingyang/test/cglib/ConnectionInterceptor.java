package com.qingyang.test.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by qingyang on 2018/2/9.
 */
public class ConnectionInterceptor implements MethodInterceptor{

    private Object object;

    public ConnectionInterceptor(Object object) {
        this.object = object;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("before");
        Object r = method.invoke(object, args);
        System.out.println("after");
        if(ConnectionImpl.class.isAssignableFrom(obj.getClass())){
            System.out.println("true");
        }
        System.out.println(r);
        return r;
    }
}
