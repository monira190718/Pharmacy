package com.sparktech.finalproject.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {
    
    @Id
    private String id;

    private String orderNumber;
    
    private String customerName;

    private String customerEmail;


    private String phone;


    private String address;


    private String medicineId;

    private String medicineName;


    private int quantity;


    private double price;


    private String status;

    private String deliveryDate;

    private String paymentMethod;

}
