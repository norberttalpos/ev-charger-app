package com.adja.evchargerappserver.api.person;


import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
@Api(value = "/api/person", tags = "Persons")
public class PersonController extends AbstractController<Person,PersonService> {

}
