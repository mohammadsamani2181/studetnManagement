package com.studentManagement.service;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails, Map<String, Object> claims);
    <T> T extractClaim(String token, Function<Claims, T> claimsTFunction);
}

