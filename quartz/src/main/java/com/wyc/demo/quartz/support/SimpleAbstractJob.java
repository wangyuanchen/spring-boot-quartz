package com.wyc.demo.quartz.support;

import com.wyc.demo.entity.ScheduleJob;
import org.quartz.JobDataMap;


/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:41
 * @description:
 */
public abstract class SimpleAbstractJob extends CommonAbstractJob {

    /**
     * 获取执行表达式
     * @return
     */
    public abstract String getCronExpression();

    /**
     * 获取JobDataMap
     * @return
     */
    public abstract JobDataMap getJobDataMap();

    /**
     * 获取JobName
     * @return
     */
    public abstract String getJobName();

    public void startJob() throws Exception {
        String cronExpression = getCronExpression();
        //jobName不要包含时间戳,和group不能同时重复
        ScheduleJob job = new ScheduleJob(getJobName(), getJobGroup(), cronExpression, getClass());
        enableSchedule(job, getJobDataMap());
        logger.info("---设置完成:{}---", cronExpression);
    }

    /**
     * 停止定时器 
     *
     * @throws Exception
     */
    public void stopJob() throws Exception {
        scheduleJobService.removeSchedule(getJobName(), getJobGroupString());
    }

    /**
     * 立即运行定时器 
     *
     * @param delete
     * @param block
     * @throws Exception
     */
    public void runJob(boolean delete, boolean block) throws Exception {
        scheduleJobService.execSchedule(getJobName(), getJobGroupString(), getJobDataMap(), delete, block);
    }

}
