/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.interfacecall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
 * @author TUANPLA
 */
public class Controller {

    public static void main(String[] args) throws ClassNotFoundException {
        Class interfaceClass = IAnyThing.class;
        ClassLoader classLoader = interfaceClass.getClassLoader();
        Class<?>[] interfaces = new Class<?>[] { interfaceClass };
        
//        String interfaceName = "dev.interfacecall.IAnyThing";
//        ClassLoader classLoader = Class.forName(interfaceName).getClassLoader();
//        Class<?>[] interfaces = new Class<?>[]{Class.forName(interfaceName)};
        InvocationHandler handler = new AnyInvocationHandler();
        IAnyThing anyThing = (IAnyThing) Proxy.newProxyInstance(classLoader, interfaces, handler);
        anyThing.doSomething();
    }
}
