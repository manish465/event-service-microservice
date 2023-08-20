package com.manish.user.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class Convertor {
    public static String extractAuthoritiesToString(Collection<? extends GrantedAuthority> authorities){
        StringBuilder roles = new StringBuilder();

        for(GrantedAuthority authority : authorities){
            String authorityString = authority.toString();

            if(!roles.isEmpty()){
                roles.append(",");
            }
            roles.append(authorityString);
        }

        return roles.toString();
    }
}
