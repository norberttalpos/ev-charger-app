package com.adja.evchargerappserver.api.role;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Role")
@Table(name = "role")
@Getter
@Setter
public class Role extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;
}
