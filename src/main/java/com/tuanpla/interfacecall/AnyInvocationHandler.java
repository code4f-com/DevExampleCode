/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.interfacecall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author TUANPLA
 */
public class AnyInvocationHandler implements InvocationHandler {

    private static final Method doSomething;

    static {
        try {
            doSomething = IAnyThing.class.getMethod("doSomething");
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return handleObjectMethod(proxy, method, args);
        }

        if (doSomething.equals(method)) {
            doSomethingImpl();
            return null;
        }

        throw new AbstractMethodError(method.toString());
    }

    private Object handleObjectMethod(Object proxy, Method method, Object[] args) {
        switch (method.getName()) {
            case "equals":
                return proxy == args[0];
            case "hashCode":
                return System.identityHashCode(proxy);
            case "toString":
                return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy));
            default:
                throw new AssertionError();
        }
    }

    private void doSomethingImpl() {
        // implement....
    }

}
