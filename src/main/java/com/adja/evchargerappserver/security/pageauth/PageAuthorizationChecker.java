package com.adja.evchargerappserver.security.pageauth;

import com.adja.evchargerappserver.api.role.Role;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PageAuthorizationChecker {

    public static boolean hasRightForPage(String route, Collection<Role> roles) {
        Collection<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());

        if(roleNames.contains("role_admin")) {
            return true;
        }
        else {
            //TODO route alapjan eldönteni van-e joga role_user
            //TODO ha be van lépve, akkor legyen letiltva login, sign-up?

            Collection<String> routes = new HashSet<>();
            routes.add("/map");
            routes.add("/");

            if(roleNames.contains("role_user"))
                return routes.contains(route);

            return false;
        }
    }

    public static Collection<String> noRightPages() {
        return Arrays.asList("/sign-up","/login");
    }
}
