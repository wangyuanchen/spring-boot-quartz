package com.wyc.demo.config;

import com.wyc.demo.quartz.support.service.ScheduleJobService;
import com.wyc.demo.quartz.support.spring.AutowiringSpringBeanJobFactory;
import org.quartz.CronTrigger;
import org.quartz.core.QuartzScheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:25
 * @description:
 */
@Configuration
@ConditionalOnClass(QuartzScheduler.class)
@ConditionalOnProperty(prefix = "quartz", name = "enabled", havingValue = "true", matchIfMissing = true)
public class QuartzConfig {

    @Autowired(required = false)
    private List<CronTrigger> triggers = new ArrayList<>();

    @Bean
    @ConditionalOnMissingBean(name = "schedulerFactory")
    public SchedulerFactoryBean schedulerFactory(DataSource quartzDataSource, JobFactory jobFactory, DataSourceTransactionManager quartzTransactionManager) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setDataSource(quartzDataSource);
        bean.setTransactionManager(quartzTransactionManager);
        bean.setApplicationContextSchedulerContextKey("applicationContextKey");
//        bean.setConfigLocation(new ClassPathResource("quartz.properties"));
        bean.setJobFactory(jobFactory);
        bean.setTriggers(triggers.toArray(new CronTrigger[]{}));
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean(JobFactory.class)
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }
    
    @Bean
    @ConditionalOnMissingBean(ScheduleJobService.class)
    public ScheduleJobService scheduleJobService() {
        return new ScheduleJobService();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor bean = new ThreadPoolTaskExecutor();
        bean.setCorePoolSize(5);// 核心线程数，默认为1
        bean.setMaxPoolSize(50);// 最大线程数，默认为Integer.MAX_VALUE
        bean.setQueueCapacity(1000);// 队列最大长度，一般需要设置值>=notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
        bean.setKeepAliveSeconds(300);// 线程池维护线程所允许的空闲时间，默认为60s
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        // AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
        // CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
        // DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
        // DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
        bean.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return bean;
    }

}
