package com.drms.disaster_relief.security;

import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String loginIdentifier) throws UsernameNotFoundException {
        Auth auth = authRepository.findByLoginIdentifier(loginIdentifier)
                .orElseThrow(() -> new UsernameNotFoundException("user nor found: " + loginIdentifier));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(loginIdentifier)
                    .password(auth.getPassword())
                    .roles(auth.getRole().toString())
                    .build();
    }
}




/*

     Explanation

                when a user logs in, he enters username and password. Spring Security (SS) receives the data.
                SS cannot read the auth table to check that. it can only understand the "UserDetails" interfave
                . this is why we are implementing this class with userdetail interface. we override the method of
                userdetail interface and this method users the authrepository interface and gets the auth data from auth
                table in database. if the auth exists, it gets the username(loginidentifier), password,
                and role. at the last the build() method. this takes the data which came from auth table and parse
                it into userdetail object which SS can understand. so it returns that.
                SS then matches our password (databse) and login(given) . if matches give access otherwise not.

                 if auth does not exist, shows the usernotfound exception.



 */
