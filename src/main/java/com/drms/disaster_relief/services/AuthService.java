package com.drms.disaster_relief.services;

import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.User;
import com.drms.disaster_relief.repository.AuthRepository;
import com.drms.disaster_relief.repository.UserRepository;
import com.drms.disaster_relief.security.JWTUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtill jwtUtill;

    @Transactional
    public String signup(Map<String, Object> requestData) {

        User user = createUserObject(requestData);   // create user object
        User savedUser = userRepository.save(user);  // save user object in user table

        String password = (String) requestData.get("password");   // get password from map

        Auth auth = new Auth();      //   creating auth object
        auth.setLoginIdentifier(savedUser.getEmail());
        auth.setPassword(passwordEncoder.encode(password));
        auth.setRole("USER");
        auth.setEntityType("CITIZEN");
        auth.setEntityId(savedUser.getUserId());
        auth.setActive(true);

        authRepository.save(auth);     //  save auth object in auth table
        return "User registered successfully";
    }


    public String login(Map<String, String> loginData) {
        String email = (String) loginData.get("email");
        String password = (String) loginData.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));  //  this line calls userdetailserviceimpl to find auth detail from db
                                                                                                    //  takes password, uses Bcryptpassword algo and matches it with hash password saved in db. if matches go forward otherwise not.
        return jwtUtill.generateToken(email);
    }



    private User createUserObject(Map<String, Object> requestData) {
        User user = new User();
        user.setFirstName((String) requestData.get("firstName"));
        user.setLastName((String) requestData.get("lastName"));
        user.setEmail((String) requestData.get("email"));
        user.setPhoneNumber((String) requestData.get("phoneNumber"));
        user.setCnic((String) requestData.get("cnic"));
        user.setCity((String) requestData.get("city"));

        return user;
    }
}
