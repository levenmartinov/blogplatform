package com.blog.blogplatform.config;

import com.blog.blogplatform.model.User;
import com.blog.blogplatform.repository.RoleRepository;
import com.blog.blogplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.blog.blogplatform.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            //Role create
            Role userRole = new Role(null, "USER");
            Role adminRole = new Role(null, "ADMIN");

            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            //User create
            User user = new User(null, "leven", passwordEncoder.encode
                    ("1234"), Set.of(userRole));
            User admin = new User(null, "admin", passwordEncoder.encode
                    ("admin123"), Set.of(userRole, adminRole));

            userRepository.save(user);
            userRepository.save(admin);

        };
    }
}
