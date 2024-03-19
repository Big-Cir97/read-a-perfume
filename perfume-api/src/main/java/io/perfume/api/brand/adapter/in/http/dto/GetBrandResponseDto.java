package io.perfume.api.brand.adapter.in.http.dto;

import io.perfume.api.brand.application.port.in.dto.BrandResult;
import io.perfume.api.brand.application.port.in.dto.GetBrandResult;

import java.util.List;

public record GetBrandResponseDto(Long id, String name, String story, String brandUrl, String thumbnail) {
    public static List<GetBrandResponseDto> of(GetBrandResult result) {
        final List<BrandResult> brandResults = result.brands();
        return brandResults.stream().map(
                brandResult ->
                        new GetBrandResponseDto(
                                brandResult.id(),
                                brandResult.name(),
                                brandResult.story(),
                                brandResult.brandUrl(),
                                brandResult.thumbnail()
                        )
        )
        .toList();
    }
}
