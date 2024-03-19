package io.perfume.api.common.config.batch.batchItem;

import io.perfume.api.batch.adapter.port.out.mapper.BrandFieldSetMapper;
import io.perfume.api.batch.adapter.port.out.mapper.CustomLineMapper;
import io.perfume.api.brand.adapter.out.persistence.brand.BrandEntity;
import io.perfume.api.brand.adapter.out.persistence.brand.BrandMapper;
import io.perfume.api.brand.application.port.out.BrandRepository;
import io.perfume.api.brand.domain.Brand;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@RequiredArgsConstructor
public class BrandBatchItem {

    private final BrandRepository brandRepository;

    private final BrandFieldSetMapper brandFieldSetMapper;

    private final CustomLineMapper lineMapper;

    private final BrandMapper brandMapper;

    public Step fileReadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fileReadStep", jobRepository)
                .<Brand, BrandEntity>chunk(1000, transactionManager)
                .reader(brandItemReader())
                .processor(itemProcessor())
                .writer(brandWriter())
                .build();
    }

    public ItemWriter<? super BrandEntity> brandWriter() {
        return brandRepository::saveAll;
    }

    public ItemProcessor<Brand, BrandEntity> itemProcessor() {
        return new ItemProcessor<Brand, BrandEntity>() {
            @Override
            public BrandEntity process(@NotNull Brand brand) {
                return brandMapper.toEntity(brand);
            }
        };
    }

    public FlatFileItemReader<Brand> brandItemReader() {
        return new FlatFileItemReaderBuilder<Brand>()
                .name("brandItemReader")
                .resource(new FileSystemResource(
                        "perfume-api/src/main/resources/csv/brands.csv"))
                .linesToSkip(1)
                .lineMapper(lineMapper.mapper(brandFieldSetMapper))
                .build();
    }
}
