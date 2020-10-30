package com.wyc.demo.entity;


import com.wyc.demo.enums.JobGroupType;
import org.quartz.Job;

import java.io.Serializable;

/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:25
 * @description:
 */
public class ScheduleJob implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3454363184589312090L;
    private String jobName;
    private JobGroupType jobGroup;
    private Integer jobStatus;
    private String cronExpression;
    private String desc;
    private Class<? extends Job> jobExecuteClass;

    public ScheduleJob() {
        super();
    }

    public ScheduleJob(String jobName, JobGroupType jobGroup) {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
    }

    public ScheduleJob(String jobName, JobGroupType jobGroup, String cronExpression) {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cronExpression = cronExpression;
    }

    public ScheduleJob(String jobName, JobGroupType jobGroup, String cronExpression,
                       Class<? extends Job> jobExecuteClass) {
        super();
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cronExpression = cronExpression;
        this.jobExecuteClass = jobExecuteClass;
    }

    public String getTriggerName() {
        return this.getJobName();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobGroupType getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(JobGroupType jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<? extends Job> getJobExecuteClass() {
        return jobExecuteClass;
    }

    public void setJobExecuteClass(Class<? extends Job> jobExecuteClass) {
        this.jobExecuteClass = jobExecuteClass;
    }
}
