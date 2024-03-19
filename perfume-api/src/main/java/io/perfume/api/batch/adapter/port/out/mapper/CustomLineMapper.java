package io.perfume.api.batch.adapter.port.out.mapper;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

@Component
public class CustomLineMapper {
    public <T> LineMapper<T> mapper(FieldSetMapper<T> mapper) {
        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(mapper);
        return lineMapper;
    }
}
