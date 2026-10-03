package com.sparktech.finalproject.registration;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;



@Controller
@RequiredArgsConstructor
public class RegistrationController {



    private final UserService userService;



    // ==============================
    // Open Thymeleaf Registration Page
    // ==============================

    @GetMapping("/registration")
    public String registrationPage(Model model){

        model.addAttribute("user", new User());

        return "registration";

    }
    
    // ==============================
    // Thymeleaf Registration Form Submit
    // ==============================

    @PostMapping("/registration")
    public String registerFromPage(

            @Valid @ModelAttribute("user") User user,

            BindingResult result,

            @RequestParam("confirmPassword") String confirmPassword,

            Model model

    ){

        if(result.hasErrors()){
            return "registration";
        }


        System.out.println("Password: " + user.getPassword());
        System.out.println("Confirm: " + confirmPassword);


        if(!user.getPassword().equals(confirmPassword)){

            model.addAttribute(
                    "error",
                    "Password and Confirm Password do not match"
            );

            return "registration";
        }


        if(userService.emailExists(user.getEmail())){

            model.addAttribute(
                    "error",
                    "Email already registered"
            );

            return "registration";
        }


        userService.registerUser(user);


        return "redirect:/login";
    }

    // ==============================
    // REST API Registration
    // ==============================

    @PostMapping("/api/auth/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(

            @Valid @RequestBody User user

    ){

        // Duplicate email check

        if(userService.emailExists(user.getEmail())){


            return ResponseEntity

                    .status(HttpStatus.BAD_REQUEST)

                    .body("Email already registered");

        }

        // Save user

        User savedUser = userService.registerUser(user);
        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(savedUser);

    }



}