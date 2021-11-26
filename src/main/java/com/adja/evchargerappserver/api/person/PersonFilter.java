package com.adja.evchargerappserver.api.person;

import lombok.Data;

@Data
public class PersonFilter {
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String roleName;
    private Long car;
    private Boolean hasCar;
    private Boolean observingCharger;
}
