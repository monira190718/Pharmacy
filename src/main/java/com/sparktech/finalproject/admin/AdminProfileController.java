package com.sparktech.finalproject.admin;

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
@RequestMapping("/admin")
public class AdminProfileController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    @PostMapping("/profile/update")
    public String updateProfile(

            @ModelAttribute User admin,
            Authentication authentication

    ){


        String email =
                authentication.getName();



        User oldUser =
                userRepository
                        .findByEmail(email)
                        .orElse(null);



        oldUser.setFullname(
                admin.getFullname()
        );


        oldUser.setPhone(
                admin.getPhone()
        );


        oldUser.setAddress(
                admin.getAddress()
        );



        userRepository.save(oldUser);



        return "redirect:/admin";

    }





    @PostMapping("/profile/password")
    public String changePassword(

            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Authentication authentication

    ){


        String email =
                authentication.getName();



        User user =
                userRepository
                        .findByEmail(email)
                        .orElse(null);



        if(passwordEncoder.matches(
                oldPassword,
                user.getPassword()
        )){


            user.setPassword(
                    passwordEncoder.encode(newPassword)
            );


            userRepository.save(user);

        }



        return "redirect:/admin";

    }

}
