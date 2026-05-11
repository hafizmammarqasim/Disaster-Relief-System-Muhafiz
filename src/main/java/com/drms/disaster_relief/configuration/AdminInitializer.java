package com.drms.disaster_relief.configuration;

import com.drms.disaster_relief.entity.Auth;
import com.drms.disaster_relief.repository.AuthRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner createSuperAdmin(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Check if the Admin already exists in the database
            String adminEmail = "admin@drms.com";

            if (authRepository.findByLoginIdentifier(adminEmail).isEmpty()) {

                // 2. Create the Admin Auth record
                Auth admin = new Auth();
                admin.setLoginIdentifier(adminEmail);

                // 3. Encode the password "admin123"
                admin.setPassword(passwordEncoder.encode("admin123"));

                // 4. Set the roles and labels
                admin.setRole("ADMIN"); // Just "ADMIN" as you requested
                admin.setEntityType("SUPER_ADMIN");
                admin.setActive(true);

                // 5. Save to the database
                authRepository.save(admin);

                System.out.println(">> SUCCESS: Super Admin 'admin@drms.com' has been created with password 'admin123'");
            } else {
                System.out.println(">> INFO: Super Admin already exists in the database.");
            }
        };
    }
}
