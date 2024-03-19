package io.perfume.api.brand.application.port.out;

import io.perfume.api.brand.adapter.out.persistence.brand.BrandEntity;
import io.perfume.api.brand.domain.Brand;
import org.springframework.batch.item.Chunk;

import java.util.List;

public interface BrandRepository {
  Brand save(Brand brand);

  List<Brand> saveAll(List<Brand> brands);

  // List<BrandEntity>
  void saveAll(Chunk<? extends BrandEntity> brands);

}
