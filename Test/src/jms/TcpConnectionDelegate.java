package jms;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by qingyang on 2018/1/25.
 */
public class TcpConnectionDelegate implements InvocationHandler{
    private Object target;

    public TcpConnectionDelegate(Object target) {
        this.target = target;
    }

    public Object bind(){
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), this.target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("delegate do first");
        Object r = method.invoke(target, args);
        System.out.println("delegate do last");
        if(proxy instanceof Proxy){
            System.out.println("is subclass of proxy");
        }
        return r;
    }
}
