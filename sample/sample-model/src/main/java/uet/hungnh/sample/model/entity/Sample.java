package uet.hungnh.sample.model.entity;

import uet.hungnh.common.model.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "sample_name_unq")})
public class Sample extends BaseEntity {

    private static final long serialVersionUID = 1427168249598037052L;

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
