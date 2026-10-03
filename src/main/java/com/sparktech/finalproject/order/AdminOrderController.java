package com.sparktech.finalproject.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderRepository orderRepository;



    @GetMapping("/orders")
    public String orders(Model model){


        model.addAttribute(
                "orders",
                orderRepository.findAll()
        );


        return "admin";

    }



    @PostMapping("/orders/status/{id}")
    public String updateStatus(
            @PathVariable String id,
            @RequestParam String status
    ){


        Order order =
                orderRepository.findById(id)
                        .orElse(null);



        if(order != null){

            order.setStatus(status);

            orderRepository.save(order);

        }


        return "redirect:/admin";

    }
}
