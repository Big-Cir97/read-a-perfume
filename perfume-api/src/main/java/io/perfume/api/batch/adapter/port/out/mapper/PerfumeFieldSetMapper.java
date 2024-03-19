package io.perfume.api.batch.adapter.port.out.mapper;

import io.perfume.api.perfume.domain.Perfume;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class PerfumeFieldSetMapper implements FieldSetMapper<Perfume> {

    @NotNull
    @Override
    public Perfume mapFieldSet(FieldSet fieldSet) {
        return Perfume.builder()
                .id(fieldSet.readLong(0))
                .name(fieldSet.readString(1))
                // .brandId(fieldSet.readString(2))
                // .description(fieldSet.readString(3))
                // .thumbnailId(fieldSet.readString(4))
                .build();
    }
}
