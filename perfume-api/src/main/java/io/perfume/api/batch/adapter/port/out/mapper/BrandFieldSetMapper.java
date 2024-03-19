package io.perfume.api.batch.adapter.port.out.mapper;


import io.perfume.api.brand.domain.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class BrandFieldSetMapper implements FieldSetMapper<Brand> {

    @NotNull
    @Override
    public Brand mapFieldSet(FieldSet fieldSet)  {
        return Brand.builder()
                .id(fieldSet.readLong(0))
                .name(fieldSet.readString(1))
                .brandUrl(fieldSet.readString(2))
                .build();
    }
}
