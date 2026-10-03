package com.sparktech.finalproject;


import com.sparktech.finalproject.category.Category;
import com.sparktech.finalproject.category.CategoryRepository;
import com.sparktech.finalproject.manufacturer.ManufacturerRepository;
import com.sparktech.finalproject.medicine.Medicine;
import com.sparktech.finalproject.medicine.MedicineRepository;
import com.sparktech.finalproject.order.Order;
import com.sparktech.finalproject.order.OrderRepository;
import com.sparktech.finalproject.registration.CustomUserDetails;
import com.sparktech.finalproject.registration.User;
import com.sparktech.finalproject.registration.UserRepository;
import com.sparktech.finalproject.review.Review;
import com.sparktech.finalproject.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class PageController {



    private final MedicineRepository medicineRepository;

    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final UserRepository userRepository;
     private  final OrderRepository orderRepository;

    private final ReviewRepository reviewRepository;



    @GetMapping("/admin")
    public String admin(
            Model model,
            Authentication authentication
    ){

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();



        model.addAttribute(
                "username",
                userDetails.getFullname()
        );



        model.addAttribute(
                "medicines",
                medicineRepository.findAll()
        );



        List<Category> categories =
                categoryRepository.findAll();



        for(Category category : categories){


            int count =
                    medicineRepository
                            .countByCategory(
                                    category.getCategoryName()
                            );


            category.setTotalMedicines(count);

        }



        model.addAttribute(
                "categories",
                categories
        );

        model.addAttribute(
                "manufacturers",
                manufacturerRepository.findAll()
        );

        model.addAttribute(
                "expiryMedicines",
                medicineRepository.findAllByOrderByExpiryDateAsc()
        );

        model.addAttribute(
                "orders",
                orderRepository.findAll()
        );

        model.addAttribute(
                "payments",
                orderRepository.findAll()
        );

        model.addAttribute(
                "customers",
                userRepository.findByRole("CUSTOMER")
        );

        model.addAttribute(
                "reviews",
                reviewRepository.findAll()
        );

        String email = authentication.getName();


        User admin =
                userRepository
                        .findByEmail(email)
                        .orElse(null);


        model.addAttribute(
                "admin",
                admin
        );

// =============================
// DASHBOARD DYNAMIC STATISTICS
// =============================


// All Orders
        List<Order> allOrders =
                orderRepository.findAll();


// 1. SALES
// Only Delivered orders will be counted as sales

        double totalSales =
                allOrders.stream()

                        .filter(order ->
                                "Delivered".equalsIgnoreCase(
                                        order.getStatus()
                                )
                        )

                        .mapToDouble(Order::getPrice)

                        .sum();


        model.addAttribute(
                "totalSales",
                totalSales
        );



// 2. PURCHASES
// Total purchase amount from all manufacturers

        double totalPurchases =
                manufacturerRepository.findAll()

                        .stream()

                        .mapToDouble(
                                manufacturer ->
                                        manufacturer.getTotalPurchaseAmount()
                        )

                        .sum();


        model.addAttribute(
                "totalPurchases",
                totalPurchases
        );

// 3. PROFIT & LOSS

        double profitLoss =
                totalSales - totalPurchases;


        model.addAttribute(
                "profitLoss",
                profitLoss
        );


        model.addAttribute(
                "isProfit",
                profitLoss >= 0
        );

// 3. TOTAL MEDICINES

        long totalMedicines =
                medicineRepository.count();


        model.addAttribute(
                "totalMedicines",
                totalMedicines
        );



// 4. LOW STOCK MEDICINES
// Here 5 is the low-stock threshold

        // 4. MEDICINE STATISTICS

        List<Medicine> allMedicines =
                medicineRepository.findAll();


        long totalMedicineCount =
                allMedicines.size();


// Low Stock = stock 1 to 5
        long lowStockCount =
                allMedicines.stream()
                        .filter(medicine ->
                                "Low Stock".equalsIgnoreCase(
                                        medicine.getStatus()
                                )
                        )
                        .count();


        long availableCount =
                allMedicines.stream()
                        .filter(medicine ->
                                "In Stock".equalsIgnoreCase(
                                        medicine.getStatus()
                                )
                        )
                        .count();


        long outOfStockCount =
                allMedicines.stream()
                        .filter(medicine ->
                                "Out of Stock".equalsIgnoreCase(
                                        medicine.getStatus()
                                )
                        )
                        .count();


// Percentages
        double lowStockPercent = 0;
        double availablePercent = 0;
        double outOfStockPercent = 0;


        if(totalMedicineCount > 0){

            lowStockPercent =
                    (lowStockCount * 100.0)
                            / totalMedicineCount;

            availablePercent =
                    (availableCount * 100.0)
                            / totalMedicineCount;

            outOfStockPercent =
                    (outOfStockCount * 100.0)
                            / totalMedicineCount;
        }


// Send data to HTML

        model.addAttribute(
                "lowStockCount",
                lowStockCount
        );

        model.addAttribute(
                "availableCount",
                availableCount
        );

        model.addAttribute(
                "outOfStockCount",
                outOfStockCount
        );

        model.addAttribute(
                "lowStockPercent",
                lowStockPercent
        );

        model.addAttribute(
                "availablePercent",
                availablePercent
        );

        model.addAttribute(
                "outOfStockPercent",
                outOfStockPercent
        );


        List<Medicine> stockAlerts =
                medicineRepository.findAll()
                        .stream()
                        .filter(medicine ->
                                medicine.getStockQuantity() <= 5
                        )
                        .toList();


        model.addAttribute(
                "stockAlerts",
                stockAlerts
        );
        
        return "admin";

    }

    @GetMapping("/customer")
    public String customer(
            Model model,
            Authentication authentication
    ){


        String email = authentication.getName();
        User user =
                userRepository.findByEmail(email)
                        .orElse(null);

        if(user != null){

            model.addAttribute(
                    "customerName",
                    user.getFullname()
            );

            model.addAttribute(
                    "customer",
                    user
            );

        }

        // Medicine list from admin inventory

        // CUSTOMER MEDICINE LIST
        // Out of Stock medicine hidden
        // ==============================

        List<Medicine> availableMedicines =
                medicineRepository.findAll()
                        .stream()
                        .filter(medicine ->
                                medicine.getStockQuantity() > 0
                                        &&
                                        !"Out of Stock".equalsIgnoreCase(
                                                medicine.getStatus()
                                        )
                        )
                        .toList();


        model.addAttribute(
                "medicines",
                availableMedicines
        );


// All customer orders

        List<Order> orders =
                orderRepository
                        .findByCustomerEmailOrderByIdDesc(email);


        model.addAttribute(
                "orders",
                orders
        );


        // Customer reviews

        List<Review> reviews =
                reviewRepository
                        .findByCustomerEmail(email);

        if(reviews == null){
            reviews = new ArrayList<>();
        }

        model.addAttribute(
                "reviews",
                reviews
        );

        return "customer";

    }


    @GetMapping("/order/{id}")
    public String orderPage(
            @PathVariable String id,
            Model model,
            Authentication authentication
    ){


        Medicine medicine =
                medicineRepository.findById(id)
                        .orElse(null);



        User user =
                userRepository.findByEmail(authentication.getName())
                        .orElse(null);



        Order order = new Order();


        order.setMedicineId(
                medicine.getId()
        );


        order.setMedicineName(
                medicine.getMedicineName()
        );


        order.setPrice(
                medicine.getUnitPrice()
        );


        order.setCustomerName(
                user.getFullname()
        );


        order.setCustomerEmail(
                user.getEmail()
        );



        model.addAttribute(
                "order",
                order
        );



        return "order-form";

    }

    @PostMapping("/order/save")
    public String saveOrder(
            @ModelAttribute Order order
    ){

        order.setStatus("Pending");

        // quantity অনুযায়ী total price save করতে চাইলে
        order.setPrice(order.getPrice() * order.getQuantity());

        LocalDate delivery =
                LocalDate.now().plusDays(3);



        order.setDeliveryDate(
                delivery.toString()
        );

        orderRepository.save(order);
//different order
        order.setOrderNumber(
                "ORD" + System.currentTimeMillis()
        );

        return "redirect:/customer";

    }

    @GetMapping("/my-orders")
    public String myOrders(
            Model model,
            Authentication authentication
    ){


        String email = authentication.getName();


        List<Order> orders =
                orderRepository.findByCustomerEmail(email);



        model.addAttribute(
                "orders",
                orders
        );


        return "my-orders";

    }

}