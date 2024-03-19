package io.perfume.api.brand.adapter.out.persistence.brand;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.brand.application.port.out.BrandRepository;
import io.perfume.api.brand.domain.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class BrandPersistenceAdapter implements BrandRepository {

  private final BrandMapper brandMapper;

  private final BrandJpaRepository brandJpaRepository;

  @Override
  public Brand save(Brand brand) {
    BrandEntity brandEntity = brandJpaRepository.save(brandMapper.toEntity(brand));
    return brandMapper.toDomain(brandEntity);
  }

  @Override
  public List<Brand> saveAll(List<Brand> brands) {
    Assert.notNull(brands, "Entities must not be null");

    List<Brand> result = new ArrayList<>();
    for (Brand brand : brands) {
      result.add(save(brand));
    }

    return result;
  }

  @Override
  public void saveAll(Chunk<? extends BrandEntity> brands) {
    Assert.notNull(brands, "Entities must not be null");
    brandJpaRepository.saveAll(brands);
  }
}
