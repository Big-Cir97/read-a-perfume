package io.perfume.api.perfume.adapter.out.persistence.perfumeSearch;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Document(indexName = "perfume_es", writeTypeHint = WriteTypeHint.FALSE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Mapping(mappingPath = "elastic/perfume-mapping.json")
@Setting(settingPath = "elastic/perfume-setting.json")
public class PerfumeSearchDocument {

  @Id
  @Field(name = "perfume_id", type = FieldType.Long)
  private Long id;

  @Field(name = "perfume_name", type = FieldType.Text)
  private String name;

  @Field(name = "perfume_story", type = FieldType.Text)
  private String story;

  //    @Field(name = "perfume_concentration", type = FieldType.Keyword)
  //    private Concentration concentration;

  @Field(name = "perfume_shop_url", type = FieldType.Keyword)
  private String perfumeShopUrl;

  @Field(name = "perfume_brand_id", type = FieldType.Long)
  private Long brandId;

  @Field(name = "perfume_category_id", type = FieldType.Long)
  private Long categoryId;

  @Field(name = "perfume_thumbnail_id", type = FieldType.Long)
  private Long thumbnailId;

  //    @Field(name = "perfume_brand_id", type = FieldType.Long)
  //    private NotePyramidIds notePyramidIds;

  @Field(name = "perfume_create_at", type = FieldType.Date, format = DateFormat.basic_date)
  @CreatedDate
  @Column(nullable = false, updatable = false)
  private final LocalDateTime createdAt;

  //
  @Field(name = "perfume_update_at", type = FieldType.Date, format = DateFormat.basic_date)
  @LastModifiedDate
  @Column(nullable = false)
  private final LocalDateTime updatedAt;

  @Field(name = "perfume_deleted_at", type = FieldType.Date, format = DateFormat.basic_date)
  private LocalDateTime deletedAt;

  public PerfumeSearchDocument(
      Long id,
      String name,
      String story,
      String perfumeShopUrl,
      Long brandId,
      Long categoryId,
      Long thumbnailId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    // this.id = String.valueOf(id);
    this.id = id;
    this.name = name;
    this.story = story;
    this.perfumeShopUrl = perfumeShopUrl;
    this.brandId = brandId;
    this.categoryId = categoryId;
    this.thumbnailId = thumbnailId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }
}
