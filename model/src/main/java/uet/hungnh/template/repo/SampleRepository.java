package uet.hungnh.template.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.template.model.Sample;

@Repository
public interface SampleRepository extends PagingAndSortingRepository<Sample, Long> {
}
