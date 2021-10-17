package com.adja.evchargerappserver.api.person;

import com.adja.evchargerappserver.api.abstracts.AbstractService;
import com.adja.evchargerappserver.api.abstracts.NotValidUpdateException;
import com.adja.evchargerappserver.api.electriccar.ElectricCarRepository;
import com.adja.evchargerappserver.api.electriccartype.ElectricCarType;
import com.adja.evchargerappserver.api.electriccartype.QElectricCarType;
import com.adja.evchargerappserver.api.role.Role;
import com.adja.evchargerappserver.api.role.RoleRepository;
import com.adja.evchargerappserver.security.JwtUtil;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService extends AbstractService<Person, PersonFilter, PersonRepository> implements UserDetailsService {

    @Autowired
    private ElectricCarRepository electricCarRepository;

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

        return (Collection<Person>) this.repository.findAll(where);
    }

    @Override
    protected boolean validateEntity(Person person) {
        return  person.getCar() == null ||electricCarRepository.findById(person.getCar().getId()).isPresent()  ;
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
                return this.repository.save(person);
            }
        }
        else
            throw new NotValidUpdateException("");
    }

    @Override
    public Person put(Long id, Person person) throws NotValidUpdateException {
        if(this.validateEntity(person)) {

            if(person.getPassword() != null) {
                if(!person.getPassword().equals(this.getById(id).getPassword())) {
                    person.setPassword(this.passwordEncoder.encode(person.getPassword()));
                }
            }
            return this.repository.save(person);
        }
        else
            throw new NotValidUpdateException("");

    }

    @Override
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
}
