package uet.hungnh.sample.service;

import org.springframework.cache.annotation.Cacheable;
import uet.hungnh.sample.dto.SampleDTO;

import java.util.UUID;

public interface ISampleService {

    @Cacheable("sample")
    String sample() throws InterruptedException;

    SampleDTO create(SampleDTO sampleDTO);

    SampleDTO update(SampleDTO sampleDTO);

    SampleDTO retrieve(UUID id);

    void delete(UUID id);
}
