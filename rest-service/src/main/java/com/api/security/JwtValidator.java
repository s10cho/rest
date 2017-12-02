package com.api.security;

import com.api.exception.InvalidTokenException;
import com.api.exception.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.domain.entity.Member;
import com.rest.util.crypt.CryptoUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtValidator
{
    @Value("${jwt.headername}")
    private String jwtHeaderName;

    @Value("${jwt.key}")
    private String jwtKey;

    public Member validate(HttpServletRequest request) throws InvalidTokenException, TokenExpiredException
    {
        String token = request.getHeader(jwtHeaderName);

        if (token == null)
        {
            throw new InvalidTokenException("JWT key is required.");
        }

        String decryptedToken = null;

        try
        {
            decryptedToken = CryptoUtil.parseJWT(token, jwtKey);
        }
        catch (ExpiredJwtException eje)
        {
            throw new TokenExpiredException("JWT key is expired.");
        }
        catch (MalformedJwtException me)
        {
            throw new InvalidTokenException("JWT key is invalid.");
        }

        if (decryptedToken == null)
        {
            throw new InvalidTokenException("JWT key is required.");
        }

        Member member = null;

        try
        {
            ObjectMapper obj = new ObjectMapper();
            member = obj.readValue(decryptedToken, Member.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return member;
    }
}
