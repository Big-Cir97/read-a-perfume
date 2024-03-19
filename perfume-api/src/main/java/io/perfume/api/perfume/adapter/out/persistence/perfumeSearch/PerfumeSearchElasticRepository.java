package io.perfume.api.perfume.adapter.out.persistence.perfumeSearch;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PerfumeSearchElasticRepository
    extends ElasticsearchRepository<PerfumeSearchDocument, Long> {

  List<PerfumeSearchDocument> findByNameContains(String name);
}
