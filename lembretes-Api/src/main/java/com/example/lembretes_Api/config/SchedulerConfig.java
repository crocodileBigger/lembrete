package com.example.lembretes_Api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // Define o número de threads paralelas
        scheduler.setThreadNamePrefix("EmailScheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
