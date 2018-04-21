package com.circle.reflection.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author KeWeiYang
 * @date 2018/2/28 17:19
 */
public class DaoProxy implements InvocationHandler {

    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret ;
        System.out.println("事务开始！");
        ret = method.invoke(target, args);
        System.out.println("事务结束！");

        return ret;
    }
}
