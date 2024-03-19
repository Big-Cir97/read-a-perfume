package io.perfume.api.brand.application.port.in;

import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.in.dto.GetBrandResult;

public interface FindBrandUseCase {
  BrandResult findBrandById(Long id);

  BrandForPerfumeResult findBrandForPerfume(Long id);

  GetBrandResult findAll();

  GetBrandResult findAllDb();

  // List<BrandResult> findAll();
}
