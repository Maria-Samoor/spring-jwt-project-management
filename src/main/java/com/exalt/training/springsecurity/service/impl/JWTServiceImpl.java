package com.exalt.training.springsecurity.service.impl;

import com.exalt.training.springsecurity.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of the JWTService interface, providing JWT-related operations.
 */
@Service
public class JWTServiceImpl implements JWTService {

    /**
     * Generates a JWT token based on user details.
     *
     * @param userDetails the user details for generating the token.
     * @return the generated JWT token.
     */
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date (System.currentTimeMillis()+1000*60*24))
                .signWith(getSigninKey())
                .compact();

    }

    /**
     * Generates a refresh token with additional claims and user details.
     *
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details for generating the refresh token.
     * @return the generated refresh token.
     */
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date (System.currentTimeMillis()+604800000))
                .signWith(getSigninKey())
                .compact();

    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param <T>            the type of the claim.
     * @param token          the JWT token.
     * @param claimsResolver a function to resolve the claim.
     * @return the resolved claim.
     */
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the signing key used for JWT token generation.
     *
     * @return the signing key.
     */
    private SecretKey getSigninKey() {
        byte[] key = Decoders.BASE64.decode("u4e3aIn2l0JiJqF7qPimO3YxZ9/hE0oPGydXKhBkr9c=");
        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token.
     * @return the claims extracted from the token.
     */
    private Claims extractAllClaim(String token){
        return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Validates whether the provided JWT token is still valid based on user details.
     *
     * @param token       the JWT token to validate.
     * @param userDetails the user details to compare against.
     * @return true if the token is valid; otherwise, false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username= extractUserName(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    /**
     * Checks whether the JWT token has expired.
     *
     * @param token the JWT token to check.
     * @return true if the token is expired; otherwise, false.
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
