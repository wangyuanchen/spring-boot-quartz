package com.wyc.demo.quartz.job;

import com.wyc.demo.enums.JobGroupType;
import com.wyc.demo.quartz.support.SimpleAbstractJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:41
 * @description:
 */
@Component
public class DemoJob extends SimpleAbstractJob {

    private static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String cronExpression = "0 0/1 * * * ?";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date nowTime = new Date();
        logger.info("Demo-定时器===>执行Demo-定时任务开始，当前时间:{}", DATE_FORMAT.format(nowTime));
    }

    @Override
    public String getCronExpression() {
        return cronExpression;
    }

    @Override
    public JobDataMap getJobDataMap() {
        return null;
    }

    @Override
    public String getJobName() {
        return getClass().getSimpleName();
    }

    @Override
    public JobGroupType getJobGroup() {
        return JobGroupType.DEMO_JOB;
    }


}
