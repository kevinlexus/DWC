package com.dic.bill;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Используется для получения spring-bean из not spring bean
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;


    /**
     * Вернуть spring bean
     *
     * @param beanClass - класс bean
     * @param <T>
     */
    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * Уставновить spring контекст
     *
     * @param context - контекст
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContext.context = context;
    }
}