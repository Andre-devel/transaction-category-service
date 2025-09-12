package br.com.andredevel.transaction.category.service.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;
    private final String token;

    public UserAuthenticationToken(String userId, String token) {
        super(Collections.emptyList());
        this.userId = userId;
        this.token = token;
        setAuthenticated(true); 
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserAuthenticationToken{userId='" + userId + "'}";
    }
}