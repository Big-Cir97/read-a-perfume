package io.perfume.api.brand.application.port.in.dto;

import io.perfume.api.brand.domain.Brand;
import io.perfume.api.file.domain.File;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public record BrandResult(Long id, String name, String story, String brandUrl, String thumbnail) implements Serializable {
  public static BrandResult of(Brand brand, File file) {
    return new BrandResult(
        brand.getId(), brand.getName(), brand.getStory(), brand.getBrandUrl(), file.getUrl());
  }

  public static BrandResult of(Brand brand) {
    return new BrandResult(
            brand.getId(), brand.getName(), brand.getStory(), brand.getBrandUrl(), "");
  }
}
