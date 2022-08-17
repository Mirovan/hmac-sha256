package com.example.demo.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String companyId = (String) authentication.getPrincipal();

        if ( companyId != null ) {
//            MyCustomAuthentication fullyAuthenticated = new MyCustomAuthentication(null, null, null);
//            return fullyAuthenticated;
            return new UsernamePasswordAuthenticationToken(companyId, "", new ArrayList<>());
        } else {
            throw new AccessDeniedException("Invalid authetication token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}