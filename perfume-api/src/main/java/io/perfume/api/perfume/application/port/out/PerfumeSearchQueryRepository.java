package io.perfume.api.perfume.application.port.out;

import io.perfume.api.perfume.adapter.out.persistence.perfumeSearch.PerfumeSearchDocument;
import java.util.List;

public interface PerfumeSearchQueryRepository {

  List<PerfumeSearchDocument> searchByName(String name);
}
