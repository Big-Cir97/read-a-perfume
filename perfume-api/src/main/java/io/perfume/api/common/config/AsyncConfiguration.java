package io.perfume.api.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    // ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    // 스레드 풀 핵심 설정 : CorePoolSize() & MaximumPoolSize
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(8);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("AsyncThread-");
    executor.initialize();
    return executor;
  }
}
