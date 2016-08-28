package uet.hungnh.sample.service;

import uet.hungnh.sample.dto.SampleDTO;

import java.util.UUID;

public interface ISampleService {
    SampleDTO create(SampleDTO sampleDTO);

    SampleDTO update(SampleDTO sampleDTO);

    SampleDTO retrieve(UUID id);

    void delete(UUID id);
}
