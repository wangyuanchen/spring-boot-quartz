package com.wyc.demo.quartz.support.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 15:40
 * @description:用于立即触发的任务执行后取消定时器
 */
public class RemoveAfterRunListener implements JobListener {

    private int state = 0;

    @Override
    public String getName() {
        return RemoveAfterRunListener.class.getName();
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext arg0) {
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext arg0) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException arg1) {
        try {
            state = 1;
            context.getScheduler().deleteJob(context.getJobDetail().getKey());
        } catch (SchedulerException e) {
            throw new RuntimeException();
        }
    }

    public int getState() {
        return state;
    }

}
