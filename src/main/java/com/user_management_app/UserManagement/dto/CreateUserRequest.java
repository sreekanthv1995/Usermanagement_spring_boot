package com.user_management_app.UserManagement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {

        private String username;
        private String email;
        private String password;
        private String confirmPassword;
}
