package io.perfume.api.batch.adapter.port.in;

import io.perfume.api.batch.adapter.port.in.dto.JobRequestDto;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class BatchController {

  private final JobLauncher jobLauncher;

  private final JobRegistry jobRegistry;

  private final JobExplorer jobExplorer;

  public BatchController(
      JobLauncher jobLauncher, JobRegistry jobRegistry, JobExplorer jobExplorer) {
    this.jobLauncher = jobLauncher;
    this.jobRegistry = jobRegistry;
    this.jobExplorer = jobExplorer;
  }

  @PostMapping("/batch/job")
  public ExitStatus runJob(@RequestBody JobRequestDto request) throws Exception {
    // job 이름 빈 탐색
    Job job = jobRegistry.getJob(request.jobName());

    JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
            .getNextJobParameters(job)
            .toJobParameters();

    return jobLauncher.run(job, jobParameters).getExitStatus();
  }
}
