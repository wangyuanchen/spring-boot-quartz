package com.wyc.demo.quartz.support.spring;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 15:38
 * @description:
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        Object job = applicationContext.getBean(bundle.getJobDetail().getJobClass());
        if (job == null) {
            job = super.createJobInstance(bundle);
            autowireCapableBeanFactory.autowireBean(job);
        }
        return job;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

}
