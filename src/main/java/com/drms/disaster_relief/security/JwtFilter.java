package com.drms.disaster_relief.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtill jwtUtill;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");     //  get header from request
        String userName = null;     //   loginidentifier
        String token = null;    //  this is the actual token we recieve from the request


        //   format of token =  Authorization: Bearer <token>

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {   //   checks the format of token. token starts with bearer as you can see in format above.
            token = authorizationHeader.substring(7);       //  it removes the first 7 character which is Bearer + space. annd it gets the real token
            userName = jwtUtill.extractUserName(token);     //   calls method from JwtUtill class and get the username/loginidentifier
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtill.validateToken(token)) {      // this calls the method from JwtUtill and validates the token
                String role = jwtUtill.extractRoleOfUser(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null,
                        List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(role)));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // record user ip address and session id
                SecurityContextHolder.getContext().setAuthentication(authentication);  // storage for current request. once the user logs in, for every other part of system, it remebers this is verified user Ahmad etc.
                // this lasts for one request. have to do for each request.
            }
        }
        chain.doFilter(request, response);  //  it tells the request to keep moving
    }







}




/*
       Explanation:
                  JwtUtil class creates and validate the token.
                  UserDetailServiceImpl finds the user details from auth table and matches with login details.

                  this is the main class. this class receives every request from the browser. it looks into
                  the header of the request and extracts the token from that header.
                  it then asks the JwtUtill class , is this token real and not expired. if the token is real it
                  the says to SS that this is good to go.




 */
