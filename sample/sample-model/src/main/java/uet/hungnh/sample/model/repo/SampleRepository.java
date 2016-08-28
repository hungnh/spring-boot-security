package uet.hungnh.sample.model.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.sample.model.entity.Sample;

import java.util.UUID;

@Repository
public interface SampleRepository extends PagingAndSortingRepository<Sample, UUID> {
}
