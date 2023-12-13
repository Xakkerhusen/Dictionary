package org.example;

import org.example.controller.Controllerr;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Controllerr bean = applicationContext.getBean(Controllerr.class);

        bean.start();
    }
}