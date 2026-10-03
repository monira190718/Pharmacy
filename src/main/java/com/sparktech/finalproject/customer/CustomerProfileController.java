package com.sparktech.finalproject.customer;

import com.sparktech.finalproject.registration.User;
import com.sparktech.finalproject.registration.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerProfileController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute User customer,
            Authentication authentication
    ){

        String email = authentication.getName();


        User oldUser =
                userRepository
                        .findByEmail(email)
                        .orElse(null);


        if(oldUser != null){

            oldUser.setFullname(
                    customer.getFullname()
            );

            oldUser.setPhone(
                    customer.getPhone()
            );

            oldUser.setAddress(
                    customer.getAddress()
            );

            userRepository.save(oldUser);
        }


        return "redirect:/customer/profile";
    }



    @PostMapping("/profile/password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Authentication authentication
    ){

        User user =
                userRepository
                        .findByEmail(
                                authentication.getName()
                        )
                        .orElse(null);


        if(user != null &&
                passwordEncoder.matches(
                        oldPassword,
                        user.getPassword()
                )){

            user.setPassword(
                    passwordEncoder.encode(
                            newPassword
                    )
            );

            userRepository.save(user);
        }


        return "redirect:/customer/profile";
    }

    @GetMapping("/profile")
    public String profile(
            Model model,
            Authentication authentication
    ){

        User customer =
                userRepository
                        .findByEmail(
                                authentication.getName()
                        )
                        .orElse(null);

        model.addAttribute(
                "customer",
                customer
        );

        return "customer-profile";
    }
}
