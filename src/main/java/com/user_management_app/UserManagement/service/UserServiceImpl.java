//package com.user_management_app.UserManagement.service;
//
//import com.user_management_app.UserManagement.entity.User;
//import com.user_management_app.UserManagement.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService{
//
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void saveRegister(User user) {
//         userRepository.save(user);
//    }
//
//    @Override
//    public void deleteById(int id) {
//        userRepository.deleteById(id);
//    }
//
//    @Override
//    public User findById(int id) {
//
//        User theUser = null;
//        Optional<User> result = userRepository.findById(id);
//        if (result.isPresent()){
//            theUser = result.get();
//        }else {
//            throw new RuntimeException("Not found user - id : "+id);
//        }
//        return theUser;
//    }
//
//}
