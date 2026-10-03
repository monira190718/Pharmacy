package com.sparktech.finalproject;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RedirectController {


    @GetMapping("/redirect")
    public String redirect(Authentication authentication){


        String role =
                authentication.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority();



        if(role.equals("ROLE_ADMIN")){

            return "redirect:/admin";

        }


        if(role.equals("ROLE_CUSTOMER")){

            return "redirect:/customer";

        }


        return "redirect:/login";


    }

}