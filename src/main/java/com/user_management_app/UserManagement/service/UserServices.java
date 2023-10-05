package com.user_management_app.UserManagement.service;

import com.user_management_app.UserManagement.dto.CreateUserRequest;
import com.user_management_app.UserManagement.entity.User;
import com.user_management_app.UserManagement.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Data
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    int flag = 0;


    public boolean createUser(CreateUserRequest createUserRequest){
        Optional<User>userByUserName = userRepository.findByUsername(createUserRequest.getUsername());
        Optional<User>userByEmail = userRepository.findByEmail(createUserRequest.getEmail());

        if(userByUserName.isPresent()){
            this.flag = 1;
            return false;

        } else if (userByEmail.isPresent()) {
            this.flag = 1;
            return false;

        } else if (createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            User newUser = User.builder()
                    .username(createUserRequest.getUsername())
                    .email(createUserRequest.getEmail())
                    .password(encoder.encode(createUserRequest.getPassword()))
                    .roles("ROLE_USER")
                    .build();
            userRepository.save(newUser);
            return true;
        }
        return false;
    }
    public void updateUser(Integer id,User user){
        Optional<User> user_db = userRepository.findById(id);
        User theUser = user_db.get();

        theUser.setUsername(user.getUsername());
        theUser.setEmail(user.getEmail());
        theUser.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(theUser);
    }

}
