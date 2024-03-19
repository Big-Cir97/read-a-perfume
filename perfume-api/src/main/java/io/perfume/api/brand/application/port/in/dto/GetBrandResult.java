package io.perfume.api.brand.application.port.in.dto;

import java.io.Serializable;
import java.util.List;

public record GetBrandResult(List<BrandResult> brands) implements Serializable {

    public static GetBrandResult of(final List<BrandResult> result) {
        return new GetBrandResult(result);
    }
}
