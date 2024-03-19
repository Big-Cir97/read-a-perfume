package io.perfume.api.common.config.batch.batchItem;//package io.perfume.api.common.batchItem;
//
//import io.perfume.api.batch.adapter.port.out.mapper.CustomLineMapper;
//import io.perfume.api.batch.adapter.port.out.mapper.PerfumeFieldSetMapper;
//import io.perfume.api.perfume.application.port.out.PerfumeRepository;
//import io.perfume.api.perfume.domain.Perfume;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Component
//@RequiredArgsConstructor
//public class PerfumeBatchItem {
//
//    private final PerfumeFieldSetMapper perfumeFieldSetMapper;
//
//    private final CustomLineMapper lineMapper;
//
//    private final PerfumeRepository perfumeRepository;
//
//    public Step fileReadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("fileReadStep", jobRepository)
//                .<Perfume, Perfume>chunk(10, transactionManager)
//                .reader(perfumeItemReader())
//                .writer(perfumeWriter())
//                .build();
//    }
//
//    public ItemWriter<Perfume> perfumeWriter() {
//        return perfumeRepository::saveAll;
//    }
//
//    public ItemProcessor<Perfume, Perfume> itemProcessor() {
//        return new ItemProcessor<Perfume, Perfume>() {
//            @Override
//            public Perfume process(Perfume item) throws Exception {
//                // <1, 2> 1 -> 2로 데이터 가공
//                return new Perfume(item);
//            }
//        };
//    }
//
//    public FlatFileItemReader<Perfume> perfumeItemReader() {
//        return new FlatFileItemReaderBuilder<Perfume>()
//                .name("perfumeItemReader")
//                // 해당 위치의 파일을 읽음
//                .resource(new FileSystemResource("src/main/resources/perfume_data(3).csv"))
//                .linesToSkip(1)
//                .lineMapper(lineMapper.mapper(perfumeFieldSetMapper))
//                .build();
//    }
//}
