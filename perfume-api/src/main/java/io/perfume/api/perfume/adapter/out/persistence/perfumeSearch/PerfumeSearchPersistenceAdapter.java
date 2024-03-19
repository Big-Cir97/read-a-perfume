package io.perfume.api.perfume.adapter.out.persistence.perfumeSearch;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.application.port.out.PerfumeSearchRepository;
import io.perfume.api.perfume.domain.Perfume;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumeSearchPersistenceAdapter implements PerfumeSearchRepository {

  private final PerfumeSearchElasticRepository elasticRepository;
  private final PerfumeSearchMapper perfumeSearchMapper;

  @Override
  public Perfume save(Perfume perfume) {
    PerfumeSearchDocument document = perfumeSearchMapper.toPerfumeDocument(perfume);
    elasticRepository.save(document);
    return perfumeSearchMapper.toPerfume(document);
  }
}
