package com.adja.evchargerappserver.api.person;

import com.adja.evchargerappserver.api.abstracts.AbstractEntity;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.role.Role;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "Person")
@Table(name = "person")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Person extends AbstractEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;


    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "personrolejoin",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToOne
    @JoinColumn(name="car_ID", referencedColumnName = "id")
    private ElectricCar car;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "notification",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "charger_id"))
    @ToString.Exclude
    private Collection<Charger> observedChargers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }
}
