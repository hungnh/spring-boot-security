package uet.hungnh.template.service;

import uet.hungnh.template.dto.SampleDTO;

public interface ISampleService {
    SampleDTO create(SampleDTO sampleDTO);

    SampleDTO update(SampleDTO sampleDTO);

    SampleDTO retrieve(Integer id);

    void delete(Integer id);
}
