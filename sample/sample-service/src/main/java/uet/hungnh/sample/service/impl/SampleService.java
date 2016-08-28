package uet.hungnh.sample.service.impl;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.hungnh.sample.dto.SampleDTO;
import uet.hungnh.sample.model.entity.Sample;
import uet.hungnh.sample.model.repo.SampleRepository;
import uet.hungnh.sample.service.ISampleService;

import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class SampleService implements ISampleService {

    @Autowired
    private SampleRepository sampleRepository;
    @Autowired
    private MapperFacade mapper;

    @Override
    public SampleDTO create(SampleDTO sampleDTO) {
        Sample sample = mapper.map(sampleDTO, Sample.class);
        sampleRepository.save(sample);
        return mapper.map(sample, SampleDTO.class);
    }

    @Override
    public SampleDTO update(SampleDTO sampleDTO) {
        Sample sample = sampleRepository.findOne(sampleDTO.getId());
        mapper.map(sampleDTO, sample);
        sampleRepository.save(sample);
        return mapper.map(sample, SampleDTO.class);
    }

    @Override
    public SampleDTO retrieve(UUID id) {
        Sample sample = sampleRepository.findOne(id);
        return mapper.map(sample, SampleDTO.class);
    }

    @Override
    public void delete(UUID id) {
        sampleRepository.delete(id);
    }
}
