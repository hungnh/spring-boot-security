package uet.hungnh.service;

import uet.hungnh.dto.SampleDTO;

import java.util.UUID;

public interface ISampleService {
    SampleDTO create(SampleDTO sampleDTO);

    SampleDTO update(SampleDTO sampleDTO);

    SampleDTO retrieve(UUID id);

    void delete(UUID id);
}
