package com.example.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * Created by muyz on 2017/11/9.
 */
@Service
public class TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(@Value("${token.secret}") String secret) {
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }

    public String addAuthentication(HttpServletResponse response, Authentication authentication) {
        final User user = (User)authentication.getPrincipal();
        MyUserDetails myUser = new MyUserDetails(user);
        myUser.setExpires((new Date()).getTime()+TEN_DAYS);
        String token = tokenHandler.createTokenForUser(myUser);
        response.addHeader(AUTH_HEADER_NAME, token);
        return token;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final MyUserDetails user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken( user, user.getPassword(), user.getAuthorities());
            }
        }
        return null;
    }
}
