package com.adja.evchargerappserver.security;

import com.adja.evchargerappserver.api.role.Role;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class PageAuthorizationChecker {

    public static boolean hasRightForPage(String route, Collection<Role> roles) {
        Collection<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());

        if(roleNames.contains("role_admin")) {
            return true;
        }
        else {
            //TODO route alapjan eldönteni van-e joga role_user
            return false;
        }
    }

    public static Collection<String> noRightPages() {
        return Arrays.asList("/", "/sign-up");
    }
}
