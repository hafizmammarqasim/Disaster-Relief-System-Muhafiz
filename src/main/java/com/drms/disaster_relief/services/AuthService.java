package com.drms.disaster_relief.services;

import com.drms.disaster_relief.dto.EmployeeDTO;
import com.drms.disaster_relief.dto.LoginDTO;
import com.drms.disaster_relief.dto.NgoDTO;
import com.drms.disaster_relief.dto.UserDTO;
import com.drms.disaster_relief.entity.*;
import com.drms.disaster_relief.repository.AuthRepository;
import com.drms.disaster_relief.repository.EmployeeRepository;
import com.drms.disaster_relief.repository.NGORepository;
import com.drms.disaster_relief.repository.UserRepository;
import com.drms.disaster_relief.security.JWTUtill;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    public String login(LoginDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));  //  this line calls userdetailserviceimpl to find auth detail from db
        //  takes password, uses Bcryptpassword algo and matches it with hash password saved in db. if matches go forward otherwise not.
        Auth auth = authRepository.findByLoginIdentifier(email).orElseThrow(() -> new RuntimeException("Authentication record missing for: " + email));
        return jwtUtill.generateToken(email, auth.getRole());
    }

    @Transactional    //    for user
    public String userSignUp(UserDTO request) {

        User user = createUserObject(request);
        User savedUser = userRepository.save(user);

        Auth auth = createAuthObject(savedUser.getEmail(), request.getPassword(),
                "USER", "CITIZEN",savedUser.getUserId(),true);      //   creating auth object

        authRepository.save(auth);     //  save auth object in auth table
        return "User registered successfully";
    }

    private User createUserObject(UserDTO request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCnic(request.getCnic());
        user.setCity(request.getCity());

        return user;
    }


    @Transactional
    public String createEmployee(EmployeeDTO request) {

        Employee employee = createEmployeeObject(request);
        Employee savedEmployee = employeeRepository.save(employee);

        Auth auth = createAuthObject(savedEmployee.getEmail(), request.getPassword(),
                savedEmployee.getRole(), "EMPLOYEE", savedEmployee.getEmployeeId(), true);

        authRepository.save(auth);
        return "Employee registered successfully";
    }

    private Employee createEmployeeObject(EmployeeDTO request) {
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        //   employee.setBranch((String) employeeData.get("branch"));   ******   will set this later
        employee.setCnic(request.getCnic());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setEmail(request.getEmail());
        employee.setRole(request.getRole());
        employee.setSpecialization(request.getSpecialization());
        employee.setAvailabilityStatus(request.getAvailabilityStatus());
        return employee;
    }

    @Transactional
    public String ngoRegistration(NgoDTO request) {
        NGO ngo = createNgoObject(request);
        NGO savedNGO = ngoRepository.save(ngo);

        Auth auth = createAuthObject(savedNGO.getEmail(), request.getPassword(),
                "NGO", "NGO", savedNGO.getNgoId(),false);

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

    private Auth createAuthObject(String email, String password, String role, String entityType,  UUID entityId, boolean isActive) {

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
