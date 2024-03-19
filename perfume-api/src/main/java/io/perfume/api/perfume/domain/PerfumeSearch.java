package io.perfume.api.perfume.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PerfumeSearch {

  private Long id;
  private String name;
  private String story;
  private Concentration concentration;
  private String perfumeShopUrl;
  private Long brandId;
  private Long categoryId;
  private Long thumbnailId;
  private NotePyramidIds notePyramidIds;

  @NotNull private final LocalDateTime createdAt;

  @NotNull private final LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

  public PerfumeSearch(
      Long id,
      String name,
      String story,
      Concentration concentration,
      String perfumeShopUrl,
      Long brandId,
      Long categoryId,
      Long thumbnailId,
      NotePyramidIds notePyramidIds,
      LocalDateTime now) {
    this.id = id;
    this.name = name;
    this.story = story;
    this.concentration = concentration;
    this.perfumeShopUrl = perfumeShopUrl;
    this.brandId = brandId;
    this.categoryId = categoryId;
    this.thumbnailId = thumbnailId;
    this.notePyramidIds = notePyramidIds;
    this.createdAt = now;
    this.updatedAt = now;
    this.deletedAt = null;
  }
}
