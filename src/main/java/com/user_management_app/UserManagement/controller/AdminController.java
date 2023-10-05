package com.user_management_app.UserManagement.controller;

import com.user_management_app.UserManagement.dto.CreateUserRequest;
import com.user_management_app.UserManagement.entity.User;
import com.user_management_app.UserManagement.repository.UserRepository;
import com.user_management_app.UserManagement.service.UserServices;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private  UserServices userService;
    @Autowired
    private   UserRepository userRepository;

    @GetMapping("/adminpanel")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showAdminPanel(Model model) {
        List<User> users= userRepository.findAll();
        model.addAttribute("users",users);
        return "adminpanel";
    }

    @GetMapping("/adminpanel/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getCreatePage(Model model) {
        model.addAttribute("createrequest",new CreateUserRequest());
        return "createuser";
    }

    @PostMapping("/adminpanel/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUser(@ModelAttribute("createrequest")CreateUserRequest createRequest,Model model) {
        boolean result=userService.createUser(createRequest);
        if(!result && userService.getFlag()==1) {
            model.addAttribute("error", "Username or Email Already Exists");
            return "/createuser";
        }else {
            return "redirect:/adminpanel";
        }
    }
    @GetMapping("/adminpanel/search")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String searchUsersByUsername(@RequestParam String searchTerm,Model model) {
        List<User>users= userRepository.findByUsername(searchTerm).stream()
                .collect(Collectors.toList());
        model.addAttribute("users",users);
        return "/adminpanel";
    }
@GetMapping(value={"/adminpanel/delete/{id}"})
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
    User user = userRepository.findById(id).orElseThrow();
    if (user != null) {
        if (user.getRoles().contains("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("message", "Admin cannot be deleted.");
        } else {
            userRepository.deleteById(id);
        }
    }
    return "redirect:/adminpanel";
}

    @GetMapping("/adminpanel/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUserForm(@PathVariable("id") Integer id,Model model) {
        User user=userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "updateuser";
    }

    @PostMapping("/adminpanel/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@ModelAttribute("user") User updatedUser) {
        userService.updateUser(updatedUser.getId(),updatedUser);
        return "redirect:/adminpanel";
    }






















//
//    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String listOfUsers(Model model) {
//        List<User> theUsers = userRepository.findAll();
//        model.addAttribute("users", theUsers);
//        return "list-users";
//    }
//
//    @GetMapping("/updateUser")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String showUpdateForm(@RequestParam("userId") int theId, Model theModel) {
//
//        //get the user from the DB
//        Optional<User> theUser = userRepository.findById(theId);
//        //set user in the model to prepopulate the form
//        theModel.addAttribute("user", theUser);
//        //sent over our form
//        return "update";
//    }
//    @PostMapping("/update")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String updateUser(@ModelAttribute("user") User theUser) {
//
////        List<User> theUsers = userService.getUserRepository().findAllByOrderByNameAsc();
//        userService.updateUser(theUser.getId(),theUser);
//        return "list-users";
//    }
//
//    @GetMapping("/deleteUser")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String deleteUser(@RequestParam("userId") int theId) {
//
//        userRepository.deleteById(theId);
//
//        return "redirect:/list-users";
//    }


}

