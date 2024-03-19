package io.perfume.api.perfume.application.service;

import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumeJpaEntity;
import io.perfume.api.perfume.adapter.out.persistence.perfume.PerfumeJpaRepository;
import io.perfume.api.perfume.adapter.out.persistence.perfume.mapper.PerfumeMapper;
import io.perfume.api.perfume.adapter.out.persistence.perfumeSearch.PerfumeSearchDocument;
import io.perfume.api.perfume.adapter.out.persistence.perfumeSearch.PerfumeSearchElasticRepository;
import io.perfume.api.perfume.adapter.out.persistence.perfumeSearch.PerfumeSearchMapper;
import io.perfume.api.perfume.application.port.out.PerfumeSearchQueryRepository;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PerfumeSearchService {

  private final PerfumeSearchQueryRepository searchQueryRepository;
  private final PerfumeSearchElasticRepository perfumeSearchElasticRepository;
  private final PerfumeJpaRepository jpaRepository;
  private final PerfumeMapper perfumeMapper;
  private final PerfumeSearchMapper mapper;

  public PerfumeSearchService(
      PerfumeSearchQueryRepository searchQueryRepository,
      PerfumeSearchElasticRepository perfumeSearchElasticRepository,
      PerfumeJpaRepository jpaRepository,
      PerfumeMapper perfumeMapper,
      PerfumeSearchMapper mapper) {
    this.searchQueryRepository = searchQueryRepository;
    this.perfumeSearchElasticRepository = perfumeSearchElasticRepository;
    this.jpaRepository = jpaRepository;
    this.perfumeMapper = perfumeMapper;
    this.mapper = mapper;
  }

  public void rdbmToDocument() {
    List<PerfumeJpaEntity> perfumeJpaEntities = jpaRepository.findAll();
    List<Perfume> perfume =
        perfumeJpaEntities.stream().map(perfumeMapper::toPerfume).collect(Collectors.toList());
    List<PerfumeSearchDocument> documents =
        perfume.stream().map(mapper::toPerfumeDocument).collect(Collectors.toList());
    perfumeSearchElasticRepository.saveAll(documents);
  }

  public List<PerfumeSearchDocument> findByName(String name) {
    List<PerfumeSearchDocument> perfumeSearchDocuments = searchQueryRepository.searchByName(name);
    return perfumeSearchDocuments;
  }

  public List<PerfumeSearchDocument> findAll(String name) {
    return perfumeSearchElasticRepository.findByNameContains(name);
  }
}
