package exercise;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// Print all the Spring beans that are loaded
// https://stackoverflow.com/questions/9602664/print-all-the-spring-beans-that-are-loaded

public class SpringUtil {

    public static void displayAllBeans(AnnotationConfigApplicationContext context, boolean includeStringBean) {

        System.out.println("---------------------------------------------------------------");
        if (includeStringBean) {
            String[] singletonNames = context.getDefaultListableBeanFactory().getSingletonNames();
            for (String singleton : singletonNames) {
                System.out.println(singleton);
            }
        } else {
            String[] beanNames = context.getBeanDefinitionNames();
            for(String beanName : beanNames) {
                if (beanName.contains("org.springframework") == false) {
                    System.out.println(beanName + " : " + context.getBean(beanName).getClass().toString());
                }
            }
        }
        System.out.println("---------------------------------------------------------------");
    }

}
