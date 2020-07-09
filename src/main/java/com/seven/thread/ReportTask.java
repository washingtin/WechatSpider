package com.seven.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 最爱吃小鱼
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ReportTask {

    @Value("${report.task.corePoolSize:5}")
    private int corePoolSize;

    @Value("${report.task.maxPoolSize:50}")
    private int maxPoolSize;

    @Value("${report.task.queueCapacity:2000}")
    private int queueCapacity;

    @Value("${report.task.keepAliveSeconds:60}")
    private int keepAliveSeconds;

    @Bean("reportTaskExecutor")
    public TaskExecutor mobileExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("ReportTask-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.initialize();
        return executor;
    }

}
