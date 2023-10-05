package com.user_management_app.UserManagement;

import com.user_management_app.UserManagement.entity.User;
import com.user_management_app.UserManagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserManagementApplication  {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder){
		return args -> {

			User user = User.builder().username("user")
					.email("user@gmail.com")
					.password(encoder.encode("password1"))
					.roles("ROLE_USER")
					.build();

			User admin = User.builder().username("Sreekanth")
					.email("sreekanthv1995@gmail.com")
					.password(encoder.encode("pass1"))
					.roles("ROLE_USER,ROLE_ADMIN")
					.build();

			//userRepository.save(admin);
		};
	}
}
