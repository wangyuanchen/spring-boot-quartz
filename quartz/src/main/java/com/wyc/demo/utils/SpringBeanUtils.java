package com.wyc.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:44
 * @description:
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext=applicationContext;
    }

    public static <T> T getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }
}
