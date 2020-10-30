package com.wyc.demo.enums;
/**
 * @author: wangyuanchen
 * @date: 2020-10-27 14:25
 * @description:
 */
public enum JobGroupType {
    DEMO_JOB("DEMO_JOB", "示例定时任务"),
    TEST_JOB("TEST_JOB", "测试定时任务");

    private String code;
    private String desc;

    private JobGroupType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}
