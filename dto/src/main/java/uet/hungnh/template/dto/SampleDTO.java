package uet.hungnh.template.dto;

import uet.hungnh.template.enums.SampleType;

public class SampleDTO {
    private Integer id;
    private String name;
    private SampleType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
