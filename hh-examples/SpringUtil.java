package com.oreilly.hh;

import javax.persistence.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.dao.hibernate.TrackHibernateDAO;

import com.oreilly.hh.data.*;

import java.time.LocalTime;
import java.util.*;

public class SpringUtil {

    public static void displayAllBeans(ApplicationContext context) {
        String[] allBeanNames = context.getBeanDefinitionNames();

        boolean includeStringBean = false;

        System.out.println("---------------------------------------------------------------");
        for(String beanName : allBeanNames) {
            //System.out.println(beanName);
            if (beanName.contains("org.springframework") == includeStringBean) {
                System.out.println(beanName + " : " + context.getBean(beanName).getClass().toString());
            }
        }
        System.out.println("---------------------------------------------------------------");
    }

}
