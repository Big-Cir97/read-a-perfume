package io.perfume.api.common.config.batch;

import io.perfume.api.common.config.batch.batchItem.BrandBatchItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration()
@RequiredArgsConstructor()
public class BatchConfig {

  private final JobRepository jobRepository;
  private final BrandBatchItem brandBatchItem;

  @Bean
  public Job brandFileReadAndWriteJob(PlatformTransactionManager transactionManager) {
    return new JobBuilder("brand-csv-data", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(brandBatchItem.fileReadStep(jobRepository, transactionManager))
            .build();
  }
}
