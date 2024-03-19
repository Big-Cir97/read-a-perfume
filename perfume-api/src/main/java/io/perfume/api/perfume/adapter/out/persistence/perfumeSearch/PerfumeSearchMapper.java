package io.perfume.api.perfume.adapter.out.persistence.perfumeSearch;

import io.perfume.api.perfume.domain.Perfume;
import org.springframework.stereotype.Component;

@Component
public class PerfumeSearchMapper {
  public Perfume toPerfume(PerfumeSearchDocument perfumeSearchDocument) {
    return Perfume.builder()
        // .id(Long.parseLong(perfumeSearchDocument.getId()))
        .id(perfumeSearchDocument.getId())
        .name(perfumeSearchDocument.getName())
        .story(perfumeSearchDocument.getStory())
        .notePyramidIds(null)
        // .concentration(perfumeSearchDocument.getConcentration())
        .perfumeShopUrl(perfumeSearchDocument.getPerfumeShopUrl())
        .brandId(perfumeSearchDocument.getBrandId())
        .categoryId(perfumeSearchDocument.getCategoryId())
        .thumbnailId(perfumeSearchDocument.getThumbnailId())
        .deletedAt(perfumeSearchDocument.getDeletedAt())
        .updatedAt(perfumeSearchDocument.getUpdatedAt())
        .createdAt(perfumeSearchDocument.getCreatedAt())
        .build();
  }

  public PerfumeSearchDocument toPerfumeDocument(Perfume perfume) {
    return new PerfumeSearchDocument(
        perfume.getId(),
        perfume.getName(),
        perfume.getStory(),
        perfume.getPerfumeShopUrl(),
        perfume.getBrandId(),
        perfume.getCategoryId(),
        perfume.getThumbnailId(),
        perfume.getCreatedAt(),
        perfume.getUpdatedAt(),
        perfume.getDeletedAt());
  }
}
