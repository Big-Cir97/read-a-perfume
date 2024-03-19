package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.domain.Perfume;

public interface PerfumeSearchRepository {

  Perfume save(Perfume perfume);
}
