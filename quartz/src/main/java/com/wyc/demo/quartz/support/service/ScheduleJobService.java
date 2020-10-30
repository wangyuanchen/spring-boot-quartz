package com.wyc.demo.quartz.support.service;

import com.google.common.collect.Sets;
import com.wyc.demo.entity.ScheduleJob;
import com.wyc.demo.quartz.support.listener.RemoveAfterRunListener;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.DailyTimeIntervalTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 15:42
 * @description:
 */
public class ScheduleJobService {

    @Autowired
    @Qualifier("schedulerFactory")
    private Scheduler scheduler;

    /**
     * 启用定时任务或重设定时任务的触发时间
     *
     * @param job
     * @param jobDataMap
     * @throws Exception
     */
    public void enableSchedule(ScheduleJob job, JobDataMap jobDataMap) throws Exception {
        if (job == null) {
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(job.getJobExecuteClass())
                .withIdentity(job.getJobName(), job.getJobGroup().getCode())
                .withDescription(job.getJobGroup().getDesc())
                .build();
        if (jobDataMap != null) {
            jobDetail.getJobDataMap().putAll(jobDataMap);
        }
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        //按新的cronExpression表达式构建一个新的trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getTriggerName(), job.getJobGroup().getCode())
                .withSchedule(scheduleBuilder)
                .withDescription(job.getJobGroup().getDesc())
                .build();
        Trigger exists = scheduler.getTrigger(trigger.getKey());
        if (exists != null) {
            if (exists instanceof CronTriggerImpl) {
                ((CronTriggerImpl) trigger).setPreviousFireTime(exists.getPreviousFireTime());
            } else if (exists instanceof CalendarIntervalTriggerImpl) {
                ((CalendarIntervalTriggerImpl) trigger).setPreviousFireTime(exists.getPreviousFireTime());
            } else if (exists instanceof DailyTimeIntervalTriggerImpl) {
                ((DailyTimeIntervalTriggerImpl) trigger).setPreviousFireTime(exists.getPreviousFireTime());
            } else if (exists instanceof SimpleTriggerImpl) {
                ((SimpleTriggerImpl) trigger).setPreviousFireTime(exists.getPreviousFireTime());
            }
        }
        scheduler.scheduleJob(jobDetail, Sets.newHashSet(trigger), true);
    }

    /**
     * 删除定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws Exception
     */
    public void removeSchedule(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 删除定时任务
     *
     * @param keys:jobGroup.jobName
     * @throws Exception
     */
    public void removeSchedule(String keys) throws Exception {
        String[] arr = keys.split("[.]");
        String jobName = arr[1];
        String jobGroup = arr[0];
        removeSchedule(jobName, jobGroup);
    }

    /**
     * 立即执行定时任务
     *
     * @param jobName
     * @param jobGroup
     * @param delete
     * @param block
     * @throws Exception
     */
    public void execSchedule(String jobName, String jobGroup, JobDataMap jobDataMap, boolean delete, boolean block) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.triggerJob(jobKey, jobDataMap);
        RemoveAfterRunListener afterExecListener = new RemoveAfterRunListener();
        if (delete) {//如果要执行完后立即取消定时器
            scheduler.getListenerManager().addJobListener(afterExecListener, KeyMatcher.keyEquals(jobKey));
        }
        if (block) {//如果要阻塞等待回调结果
            long start = System.currentTimeMillis();
            int state = afterExecListener.getState();
            while (state != 1 && (System.currentTimeMillis() - start) < 1000L) {
                state = afterExecListener.getState();
            }
        }
    }

    /**
     * 暂停定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws Exception
     */
    public void pauseSchedule(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws Exception
     */
    public void resumeSchedule(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.resumeJob(jobKey);
    }

    /**
     * 根据jobName和jobGroup获取jobDataMap
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws Exception
     */
    public JobDataMap getJobDataMap(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        JobDataMap jobDataMap = null;
        if (jobDetail != null) {
            jobDataMap = jobDetail.getJobDataMap();
        }
        return jobDataMap;
    }

}

