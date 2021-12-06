package com.adja.evchargerappserver.api.person;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.NotValidUpdateException;
import com.adja.evchargerappserver.api.charger.Charger;
import com.adja.evchargerappserver.api.chargingstation.ChargingStation;
import com.adja.evchargerappserver.api.electriccar.ElectricCar;
import com.adja.evchargerappserver.api.electriccar.ElectricCarRepository;
import com.adja.evchargerappserver.api.electriccar.ElectricCarService;
import com.adja.evchargerappserver.api.role.Role;
import com.adja.evchargerappserver.api.role.RoleRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class PersonService extends AbstractService<Person, PersonFilter, PersonRepository> implements UserDetailsService {

    @Autowired
    private ElectricCarRepository electricCarRepository;

    @Autowired
    private ElectricCarService electricCarService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<Person> search(PersonFilter filter) {
        QPerson person = QPerson.person;
        BooleanBuilder where = new BooleanBuilder();

        if(filter.getName() != null) {
            where.and(person.name.contains(filter.getName()));
        }
        if(filter.getUsername() != null) {
            where.and(person.username.contains(filter.getUsername()));
        }
        if(filter.getEmail() != null) {
            where.and(person.email.contains(filter.getEmail()));
        }
        if(filter.getPhoneNumber() != null) {
            where.and(person.phoneNumber.contains(filter.getPhoneNumber()));
        }
        if(filter.getRoleName() != null) {
            where.and(person.roles.any().name.contains(filter.getRoleName()));
        }
        if(filter.getCar() != null) {
            where.and(person.car.id.eq(filter.getCar()));
        }
        if(filter.getHasCar() != null) {
            where.and(person.car.id.isNotNull());
        }
        if(filter.getObservingCharger() != null) {
            if(filter.getObservingCharger()) {
                where.and(person.observedChargers.size().goe(1));
            }
            else {
                where.and(person.observedChargers.size().eq(0));
            }
        }

        return (Collection<Person>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(Person person) {
        return person.getCar() == null || electricCarRepository.findById(person.getCar().getId()).isPresent()  ;
    }

    public void addRoleToUser(String username, String roleName) throws NotValidUpdateException {
        Optional<Person> personByUsername = this.repository.findByUsername(username);
        if(personByUsername.isPresent()) {
            Person person = personByUsername.get();
            Optional<Role> roleByName = this.roleRepository.findByName(roleName);

            if(roleByName.isEmpty()) {
                throw new NotValidUpdateException("");
            }
            if(!person.getRoles().contains(roleByName.get())) {
                person.getRoles().add(roleByName.get());
                this.put(person.getId(), person);
            }
            else {
                throw new NotValidUpdateException("");
            }
        }
        else {
            throw new NotValidUpdateException("");
        }
    }

    @Override
    public Person post(Person person) throws NotValidUpdateException {
        if(this.validateEntity(person)) {
            try {
                if(this.getByUsername(person.getUsername()) != null) {
                    throw new NotValidUpdateException("");
                }
                return null;
            }
            catch (EntityNotFoundException e) {
                person.setPassword(this.passwordEncoder.encode(person.getPassword()));
                Person p = this.repository.save(person);
                if(person.getRoles()==null)
                    person.setRoles(new HashSet<>());
                addRoleToUser(person.getUsername(),"role_user");
                return p;
            }
        }
        else
            throw new NotValidUpdateException("");
    }

    @Override
    public Person put(Long id, Person person) throws NotValidUpdateException {
        if(this.validateEntity(person)) {

            if(Objects.equals(person.getPassword(), ""))
                person.setPassword(this.getById(id).getPassword());
            if(person.getPassword() != null && person.getPassword()!="") {
                if(!person.getPassword().equals(this.getById(id).getPassword())) {
                    person.setPassword(this.passwordEncoder.encode(person.getPassword()));
                }
            }
            if(person.getRoles()==null){
                person.setRoles(this.getById(id).getRoles());
            }

            return this.repository.save(person);
        }
        else
            throw new NotValidUpdateException("");
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = NotValidUpdateException.class)
    public void setCarToPerson(Long id, Long carId) throws NotValidUpdateException {
        try {
            Person person = this.getById(id);
            ElectricCar car = this.electricCarService.getById(carId);

            if(car.getDriver() != null)
                throw new NotValidUpdateException("");

            if(person.getCar() != null) {
                person.getCar().setDriver(null);
            }

            car.setDriver(person);
            person.setCar(car);

            this.put(id, person);
        }
        catch(EntityNotFoundException e) {
            throw new NotValidUpdateException("");
        }
    }

    @Override
    protected Person mapToEntity(Person persisted, Person dto) {
        dto.setRoles(persisted.getRoles());

        return dto;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personByUsername = this.repository.findByUsername(username);

        if(personByUsername.isEmpty()) {
            throw new UsernameNotFoundException("User not found in database");
        }
        else {
            Person person = personByUsername.get();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            person.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(), authorities);
        }
    }

    public Person getByUsername(String username) throws EntityNotFoundException {
        Optional<Person> personByUsername = this.repository.findByUsername(username);

        if(personByUsername.isPresent()) {
            return personByUsername.get();
        }
        else {
            throw new EntityNotFoundException();
        }
    }
    public void addInitialPerson(String username, String password){
        try {
            Person p=this.getByUsername(username);
            p.setPassword(password);
            this.put(p.getId(),p);
        }
        catch(NotValidUpdateException e) {
            e.printStackTrace();
        }
    }
    public void setInitialRole(String username,String roleName){
        try {
            this.addRoleToUser(username, roleName);
        }
        catch(NotValidUpdateException e) {
            e.printStackTrace();
        }

    }
}
