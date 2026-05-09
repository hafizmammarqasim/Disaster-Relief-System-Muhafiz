package com.drms.disaster_relief.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtill {

    @Value("${drms.jwt.secret}")
    private String SECRET_KEY;

    @Value("${drms.jwt.expirationMs}")
    private int tokenExpirey;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String loginIdentifier) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, loginIdentifier);
    }

    private String createToken(Map<String, Object> claims, String loginIdentifier) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(loginIdentifier)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirey))
                .signWith(getSigningKey())
                .compact();
    }


    public String extractUserName(String token) {
        Claims claims = extractAllClaims(token);   // here we are usinf claims object because
        return claims.getSubject();                // because we set claims while creating the token.
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());      // checks if the expirey date is before the current time or not
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}







//       Explanation

/*
       this class main function is to create token and then validate token

       Create Token:
                    "generateToken(String loginIdentifier)" method is called when a person logs in.
                    this method makes a map and calls another method "createToken()"  which actually
                    generates the token.
                    "createToken()"   this method adds data(claims map) in claims object which is empty
                    and then it sets the loginidetifier as the main key id. then it sets the issue date,
                    expirey date and then it signs all this with a secret key created by
                    "getsigingkey()" method. this method takes the secret key variable, converts it into
                    butes and then apply hmacshawkeyfor algorithm to converts this key into a java object
                    "SecretKey" it return this key object. at last the "compact()" method is called which
                    converts this java token which is java object into a json and return a string to user.
                    the user will now use this string everytime he wants to achieve a functionality.

        Validate Token:
                       whenever the user wants to do something, his token is validated first. here the main
                       method is last method which is "extractAllClaims()"
                       validateToken methos is called. it then calls isTokenExpired()  method to check if the
                       expirey date has passed or not. then it calls for another method extractexpiration. which
                        gets the expirey of token by calling the extractAllClaims method.
                        this method decodes the token. it first verify the secret key of token by calling
                        getsigingkey method. if verifies then it decrypts the token and return the payload or body
                        if the token was changes it won't cross the verufy secret key step.
                        the payload has loginidentifier, issue daate, expirey date, and role.
*/
