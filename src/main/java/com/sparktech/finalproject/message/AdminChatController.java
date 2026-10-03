package com.sparktech.finalproject.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminChatController {
    private final MessageRepository messageRepository;



    @GetMapping("/chats")
    public String chatList(Model model){


        List<Message> messages =
                messageRepository
                        .findBySender("CUSTOMER");


        Map<String, Message> uniqueCustomers =
                new LinkedHashMap<>();


        for(Message msg : messages){

            uniqueCustomers.put(
                    msg.getCustomerEmail(),
                    msg
            );

        }


        model.addAttribute(
                "customers",
                uniqueCustomers.values()
        );


        return "admin-chat-list";

    }

    @GetMapping("/chat/{email}")

    public String openChat(

            @PathVariable String email,

            Model model

    ){



        List<Message> messages =

                messageRepository
                        .findByCustomerEmailOrderByIdAsc(email);



        model.addAttribute(
                "messages",
                messages
        );



        model.addAttribute(
                "email",
                email
        );



        return "admin-chat";

    }

    @PostMapping("/chat/reply")

    public String reply(

            @RequestParam String email,

            @RequestParam String message

    ){


        Message msg =
                new Message();



        msg.setCustomerEmail(email);


        msg.setSender("ADMIN");


        msg.setMessage(message);


        msg.setDate(
                java.time.LocalDate.now().toString()
        );



        messageRepository.save(msg);



        return "redirect:/admin/chat/"+email;


    }

}
