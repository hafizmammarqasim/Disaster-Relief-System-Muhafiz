package com.drms.disaster_relief.configuration;

import com.drms.disaster_relief.security.JwtFilter;
import com.drms.disaster_relief.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailServiceImpl userDetailService;


    // this method defines the path a web request should take to be allowed
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)                                        //  we using JWT (stateless). we don't need cookies. so disable this.
                .authorizeHttpRequests(auth -> auth                                             //   permission list
                        .requestMatchers("/public/**").permitAll()                                  // url starting with /public/ is permitted to everyone (public)
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")                              // url starting with /admin/  is allowed to a person whose role is ADMIN
                        .requestMatchers("/missions/**", "/user/**").authenticated()      // once logged in with valid, any one can access these methods
                        .anyRequest().authenticated()                                                       // means every other url is locked. need token to unlock it
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // tells spring don't create sessions. server should
                // not remeber user. at each request token verification is required
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);                     //  tells spring, before log in, check JWT to see if they have valid token.

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {                 //  encrypts the password and sabes the encrypted password in database
        return new BCryptPasswordEncoder();
    }

    @Bean                                                      //  it is used to verify user credentials. do not need to write extra code.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


}





/*
      Explanation
                this class is like a rolebook. it works for whole project. with this class, we define
                who is guest who is use and who is admin. it means we can define who can access a
                specific functionality. here we define user cannot access the admin features and vice verca.

                without this class spring locks every request and no one can achieve anything.

 */