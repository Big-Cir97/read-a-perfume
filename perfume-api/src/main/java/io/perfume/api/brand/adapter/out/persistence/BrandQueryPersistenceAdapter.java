package io.perfume.api.brand.adapter.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.BrandQueryRepository;
import io.perfume.api.brand.domain.Brand;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static io.perfume.api.brand.adapter.out.persistence.QBrandEntity.brandEntity;

@PersistenceAdapter
@RequiredArgsConstructor
public class BrandQueryPersistenceAdapter implements BrandQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final BrandMapper brandMapper;

    @Override
    public Optional<Brand> findBrandById(Long id) {
        BrandEntity result = jpaQueryFactory.selectFrom(brandEntity)
                .where(brandEntity.id.eq(id)
                        .and(brandEntity.deletedAt.isNull()))
                .fetchOne();

        return Optional.ofNullable(brandMapper.toDomain(result));
    }
}
