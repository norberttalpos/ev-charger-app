package com.adja.evchargerappserver.api.role;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Role")
@Table(name = "role")
public class Role extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
