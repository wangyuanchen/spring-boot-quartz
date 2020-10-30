package com.wyc.demo.command;

import com.wyc.demo.quartz.job.DemoJob;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: wangyuanchen
 * @date: 2020-10-30 08:54
 * @description:
 */
@Component
public class JobCommandLine implements CommandLineRunner {

    @Resource
    private DemoJob demoJob;

    @Override
    public void run(String... args) throws Exception {
        demoJob.startJob();
    }
}
