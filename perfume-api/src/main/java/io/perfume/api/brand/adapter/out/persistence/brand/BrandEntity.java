package io.perfume.api.brand.adapter.out.persistence.brand;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.*;

@Entity()
@Table(
        name = "brand",
        indexes = {
                @Index(name = "idx_brand_name", columnList = "name")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class BrandEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  private String name;

  private String story;

  private String brandUrl;

  private Long thumbnailId;

  @Builder
  public BrandEntity(
      Long id,
      String name,
      String story,
      String brandUrl,
      Long thumbnailId,
      @NotNull LocalDateTime createdAt,
      @NotNull LocalDateTime updatedAt,
      @NotNull LocalDateTime deletedAt) {
    super(createdAt, updatedAt, deletedAt);
    this.id = id;
    this.name = name;
    this.story = story;
    this.brandUrl = brandUrl;
    this.thumbnailId = thumbnailId;
  }
}
