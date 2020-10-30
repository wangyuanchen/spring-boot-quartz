package com.wyc.demo.quartz.support;

import com.wyc.demo.entity.ScheduleJob;
import com.wyc.demo.enums.JobGroupType;
import com.wyc.demo.quartz.support.service.ScheduleJobService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:41
 * @description:不可直接继承此类,参考 {@link SimpleAbstractJob}
 */
abstract class CommonAbstractJob implements Job {

    @Resource
    protected ScheduleJobService scheduleJobService;
    private static final SimpleDateFormat CRON_FORMAT = new SimpleDateFormat("ss mm HH dd MM ? yyyy");

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取JobGroup
     * @return
     */
    public abstract JobGroupType getJobGroup();

    /**
     * 获取cron表达式 
     *
     * @param date
     * @return
     */
    public String parseCronExpression(Date date) {
        return CRON_FORMAT.format(date);
    }

    protected void enableSchedule(ScheduleJob job, JobDataMap jobDataMap) throws Exception {
        scheduleJobService.enableSchedule(job, jobDataMap);
    }

    public String getJobGroupString(){
        return getJobGroup().getCode();
    }

}
