package com.epam.training.utils;

import config.ServiceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ApplicationContextHandler {

    public static ApplicationContext context;

    public static ApplicationContext getContext(){
        if(context == null){
            context = new AnnotationConfigWebApplicationContext();
            ((AnnotationConfigWebApplicationContext) context).register(ServiceConfiguration.class);
            ((AnnotationConfigWebApplicationContext) context).refresh();
            return context;
        }
        return context;
    }
}
