package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.NgoDTO;
import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.entity.NGO;
import com.drms.disaster_relief.entity.User;
import com.drms.disaster_relief.enums.RoleType;
import com.drms.disaster_relief.repository.AuthRepository;
import com.drms.disaster_relief.repository.EmployeeRepository;
import com.drms.disaster_relief.repository.NGORepository;
import com.drms.disaster_relief.repository.UserRepository;
import com.drms.disaster_relief.security.JWTUtill;
import org.hibernate.type.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {


    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final NGORepository ngoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtill jwtUtill;



    public AuthService(AuthRepository authRepository, UserRepository userRepository, EmployeeRepository employeeRepository, NGORepository ngoRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtill jwtUtill) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.ngoRepository = ngoRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtill = jwtUtill;
    }
    @Transactional
    public String userSignup(Map<String, Object> requestData) {

        User user = createUserObject(requestData);   // create user object
        User savedUser = userRepository.save(user);  // save user object in user table

        String password = (String) requestData.get("password");   // get password from map

        Auth auth = new Auth();      //   creating auth object
        auth.setLoginIdentifier(savedUser.getEmail());
        auth.setPassword(passwordEncoder.encode(password));
        auth.setRole(RoleType.USER);
        auth.setEntityType("CITIZEN");
        auth.setEntityId(savedUser.getUserId());
        auth.setActive(true);

        saveAuth(auth);
        //  save auth object in auth table
        return "User registered successfully";
    }

    public void saveAuth(Auth auth){
        authRepository.save(auth);
    }


    public String login(Map<String, String> loginData) {
        String email = (String) loginData.get("email");
        String password = (String) loginData.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));  //  this line calls userdetailserviceimpl to find auth detail from db
                                                                                                        //  takes password, uses Bcryptpassword algo and matches it with hash password saved in db. if matches go forward otherwise not.
        // 2. Fetch the user's auth record from the database to get their role
        Auth auth = authRepository.findByLoginIdentifier(email).get();
        String roleName = auth.getRole().name(); // Extracts "ADMIN", "USER", etc.

        return jwtUtill.generateToken(email, roleName);
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

    public boolean isUnique(String identifier){
        //design choice
        //return false if user exists
        return !(authRepository.existsByLoginIdentifier(identifier));
    }

    public Optional<Auth> findByIdentifier(String identifier){
        return authRepository.findByLoginIdentifier(identifier);
    }

    @Transactional
    public String ngoRegistration(NgoDTO request) {
        NGO ngo = createNgoObject(request);
        NGO savedNGO = ngoRepository.save(ngo);

        Auth auth = createAuthObject(savedNGO.getEmail(), request.getPassword(), RoleType.NGO, EntityType.NGO, savedNGO.getNgoId(),false);

        authRepository.save(auth);
        return "NGO Registered Successfully";
    }

    private NGO createNgoObject(NgoDTO request) {
        NGO ngo = new NGO();

        ngo.setOrganizationName(request.getOrganizationName());
        ngo.setRegistrationNumber(request.getRegistrationNumber());
        ngo.setContactPersonName(request.getContactPersonName());
        ngo.setPhoneNumber(request.getPhoneNumber());
        ngo.setEmail(request.getEmail());
        ngo.setWebsite(request.getWebsite());
        ngo.setDescription(request.getDescription());
        ngo.setTrustScore(0);
        ngo.setActive(false);

        return ngo;
    }

    private Auth createAuthObject(String email, String password, Role role, EntityType entityType, UUID entityId, boolean isActive) {

        Auth auth = new Auth();
        auth.setLoginIdentifier(email);
        auth.setPassword(passwordEncoder.encode(password));
        auth.setRole(role);
        auth.setEntityType(entityType);
        auth.setEntityId(entityId);
        auth.setActive(isActive);

        return auth;
    }

    public List<NGO> getPendingNGOs() {
        return ngoRepository.findAllByIsActiveFalse();
    }

    @Transactional
    public String verifyNGO(UUID ngoId) {

        NGO ngo = ngoRepository.findById(ngoId).orElseThrow(()-> new RuntimeException("Ngo with id: " + ngoId + " not found in Ngo table"));
        ngo.setActive(true);
        ngoRepository.save(ngo);

        Auth auth = authRepository.findByLoginIdentifier(ngo.getEmail()).orElseThrow(()-> new RuntimeException("Ngo with id: " + ngoId + " not found in Auth table"));
        auth.setActive(true);
        authRepository.save(auth);

        return "NGO " + ngo.getOrganizationName() + " has been activated successfully";
    }
}
}
