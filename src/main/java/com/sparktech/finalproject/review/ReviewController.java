package com.sparktech.finalproject.review;

import com.sparktech.finalproject.registration.User;
import com.sparktech.finalproject.registration.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;


    @GetMapping("/review")
    public String review(){

        return "review-form";

    }


    @PostMapping("/review/save")
    public String saveReview(
            @ModelAttribute Review review,
            Authentication authentication
    ){


        User user =
                userRepository
                        .findByEmail(authentication.getName())
                        .orElse(null);



        if(user != null){

            review.setCustomerName(
                    user.getFullname()
            );


            review.setCustomerEmail(
                    user.getEmail()
            );

        }



        reviewRepository.save(review);



        return "redirect:/customer";

    }
}
