package io.perfume.api.brand.adapter.in.http;

import io.perfume.api.brand.adapter.in.http.dto.BrandResponse;
import io.perfume.api.brand.adapter.in.http.dto.GetBrandResponseDto;
import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.GetBrandResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/brands")
public class FindBrandController {

  private final FindBrandUseCase findBrandUseCase;
  private final RedisConnectionFactory redisConnectionFactory;

  @GetMapping("/{id}")
  public BrandResponse get(@PathVariable Long id) {
    return BrandResponse.of(findBrandUseCase.findBrandById(id));
  }

  @GetMapping()
  public ResponseEntity<?> getAll() {
    GetBrandResult result = findBrandUseCase.getBrandsResult();
    return ResponseEntity.ok(GetBrandResponseDto.of(result));
  }
}
