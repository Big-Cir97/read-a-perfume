package io.perfume.api.brand.application.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.perfume.api.brand.application.exception.BrandNotFoundException;
import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandForPerfumeResult;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.in.dto.GetBrandResult;
import io.perfume.api.brand.application.port.out.BrandQueryRepository;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.common.config.redis.CacheNames;
import io.perfume.api.file.application.exception.FileNotFoundException;
import io.perfume.api.file.application.port.in.FindFileUseCase;
import io.perfume.api.file.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FindBrandService implements FindBrandUseCase {
  private final BrandQueryRepository brandQueryRepository;
  private final FindFileUseCase findFileUseCase;

  @Override
  public BrandResult findBrandById(Long id) {
    Brand brand =
        brandQueryRepository.findBrandById(id).orElseThrow(() -> new BrandNotFoundException(id));
    File file =
        findFileUseCase
            .findFileById(brand.getThumbnailId())
            .orElseThrow(() -> new FileNotFoundException(id));
    return BrandResult.of(brand, file);
  }

  @Override
  public BrandForPerfumeResult findBrandForPerfume(Long id) {
    Brand brand =
        brandQueryRepository.findBrandById(id).orElseThrow(() -> new BrandNotFoundException(id));
    return BrandForPerfumeResult.of(brand);
  }

  @Override
  @CircuitBreaker(name = "CB-redis", fallbackMethod = "fallback")
  @Cacheable(cacheNames = CacheNames.BRAND, key = "'brand_list'", cacheManager = "redisCacheManager")
  public GetBrandResult findAll() {
    List<BrandResult> result = brandQueryRepository.findAll().stream()
            .map(BrandResult::of)
            .toList();
    return GetBrandResult.of(result);
  }

  public GetBrandResult fallback(Exception ex) {
    log.info("[CircuitBreaker : CLOSED] fallback method가 실행 됩니다.");
    List<Brand> brands = brandQueryRepository.findAll();
    List<BrandResult> result = brands.stream()
            .map(BrandResult::of)
            .toList();
    return GetBrandResult.of(result);
  }

  // 서킷이 OPEN 상태일 때
  public GetBrandResult fallback(CallNotPermittedException ex) {
    log.info("[CircuitBreaker : OPEN] fallback method가 실행 됩니다. exception : {}", ex.getMessage());
    List<Brand> brands = brandQueryRepository.findAll();
    List<BrandResult> result = brands.stream()
            .map(BrandResult::of)
            .toList();
    return GetBrandResult.of(result);
  }

  @Override
  public GetBrandResult findAllDb() {
    System.out.println(brandQueryRepository);
    List<Brand> brands = brandQueryRepository.findAll();
    List<BrandResult> result = brands.stream()
            .map(
                    brand -> {
//              File file =
//                  findFileUseCase
//                      .findFileById(brand.getThumbnailId()).
                      //.orElseThrow(() -> new FileNotFoundException(brand.getThumbnailId()));
//              return BrandResult.of(brand, file);
                      return BrandResult.of(brand);
                    })
            .toList();
    return GetBrandResult.of(result);
    // return new GetBrandResponseDto(result);
  }
}
