package uet.hungnh.sample.dto;

import uet.hungnh.sample.enums.SampleType;

import java.util.UUID;

public class SampleDTO {
    private UUID id;
    private String name;
    private SampleType type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SampleType getType() {
        return type;
    }

    public void setType(SampleType type) {
        this.type = type;
    }
}
