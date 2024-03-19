package io.perfume.api.perfume.adapter.out.persistence.perfumeSearch;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.perfume.application.port.out.PerfumeSearchQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.util.StringUtils;

@PersistenceAdapter
@RequiredArgsConstructor
public class PerfumeSearchQueryPersistenceAdapter implements PerfumeSearchQueryRepository {

  private final ElasticsearchOperations operations;

  @Override
  public List<PerfumeSearchDocument> searchByName(String name) {
    CriteriaQuery query = criteriaNameQuery(name);

    // , IndexCoordinates.of("perfume_es")
    SearchHits<PerfumeSearchDocument> search =
        operations.search(query, PerfumeSearchDocument.class);

    List<PerfumeSearchDocument> perfumeSearchDocuments =
        search.stream().map(SearchHit::getContent).toList();

    return perfumeSearchDocuments;
  }

  private CriteriaQuery criteriaNameQuery(String name) {
    //        Criteria criteria = new Criteria("perfume_name").contains(name);
    //
    //        CriteriaQuery query = new CriteriaQuery(criteria);
    CriteriaQuery query = new CriteriaQuery(new Criteria());
    if (StringUtils.hasText(name)) {
      query.addCriteria(Criteria.where("perfume_name").contains(name));
    }
    return query;
  }
}
