package uet.hungnh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import uet.hungnh.dto.SampleDTO;
import uet.hungnh.service.ISampleService;

import java.util.UUID;

import static uet.hungnh.security.constants.SecurityConstants.ROLE_USER;

@RestController
@RequestMapping("/sample")
@Secured({ROLE_USER})
public class SampleController {

    @Autowired
    private ISampleService sampleService;

    @GetMapping
    public String sample() {
        return "Sample OK!";
    }

    @PutMapping
    public SampleDTO create(@RequestBody SampleDTO sampleDTO) {
        return sampleService.create(sampleDTO);
    }

    @GetMapping(value = "/{id}")
    public SampleDTO retrieve(@PathVariable("id") UUID id) {
        return sampleService.retrieve(id);
    }

    @PostMapping
    public SampleDTO update(@RequestBody SampleDTO sampleDTO) {
        return sampleService.update(sampleDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        sampleService.delete(id);
    }
}
