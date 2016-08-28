package uet.hungnh.model.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uet.hungnh.model.entity.Sample;

import java.util.UUID;

@Repository
public interface SampleRepository extends PagingAndSortingRepository<Sample, UUID> {
}
