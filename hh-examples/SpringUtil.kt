package com.oreilly.hh

import org.springframework.context.ApplicationContext

fun displayAllBeans(context: ApplicationContext) {
    var allBeanNames = context.getBeanDefinitionNames()

    val includeStringBean = false;

    println("---------------------------------------------------------------");
    for(beanName in allBeanNames) {
        //println(beanName);
        if (beanName.contains("org.springframework") == includeStringBean) {
            println("${beanName} : ${context.getBean(beanName).getClass().toString()}");
        }
    }
    println("---------------------------------------------------------------");
}

