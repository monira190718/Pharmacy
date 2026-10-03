package com.sparktech.finalproject.message;

import com.sparktech.finalproject.registration.User;
import com.sparktech.finalproject.registration.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerChatController {
    private final MessageRepository messageRepository;

    private final UserRepository userRepository;



    @GetMapping("/chat")

    public String chat(

            Model model,

            Authentication authentication

    ){



        String email =
                authentication.getName();



        List<Message> messages =

                messageRepository
                        .findByCustomerEmailOrderByIdAsc(email);



        model.addAttribute(
                "messages",
                messages
        );



        return "customer-chat";


    }



    @PostMapping("/chat/send")

    public String sendMessage(

            @RequestParam String message,

            Authentication authentication

    ){



        String email =
                authentication.getName();



        User user =

                userRepository
                        .findByEmail(email)
                        .orElse(null);



        Message msg =
                new Message();



        msg.setCustomerEmail(email);


        msg.setCustomerName(
                user.getFullname()
        );



        msg.setSender(
                "CUSTOMER"
        );



        msg.setMessage(
                message
        );



        msg.setDate(
                LocalDate.now().toString()
        );



        messageRepository.save(msg);



        return "redirect:/customer/chat";


    }


}
