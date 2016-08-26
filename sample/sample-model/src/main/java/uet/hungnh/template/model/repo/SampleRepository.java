package uet.hungnh.template.model.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.template.model.entity.Sample;

import java.util.UUID;

@Repository
public interface SampleRepository extends PagingAndSortingRepository<Sample, UUID> {
}
