package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.perfume.adapter.out.persistence.perfumeSearch.PerfumeSearchDocument;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.application.service.PerfumeSearchService;
import io.perfume.api.perfume.domain.Perfume;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/perfume/search")
public class PerfumeSearchController {

  private final PerfumeSearchService service;
  private final PerfumeQueryRepository repository;

  @GetMapping()
  public List<PerfumeSearchDocument> searchByName(@RequestParam String name) {
    return service.findByName(name);
  }

  @GetMapping("/querydsl")
  public List<Perfume> searchByContainsName(@RequestParam String name) {
    return repository.searchByContainsName(name);
  }

  @GetMapping("/saved")
  public void savedDocument() {
    service.rdbmToDocument();
  }
}
