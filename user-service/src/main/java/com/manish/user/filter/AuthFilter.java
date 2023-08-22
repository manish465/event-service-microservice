package com.manish.user.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.manish.user.utils.Convertor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("|| doFilterInternal is called from AuthFilter class ||");

        String email = "ms2@gmail.com";
        String rolesString = "NORMAL";
        Collection<GrantedAuthority> authorities = Convertor.extractAuthoritiesFromString(rolesString);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
                authorities);
        log.info("|| using emial : {} and role : {} ||", email, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
