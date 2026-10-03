package com.sparktech.finalproject.manufacturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "manufacturers")
public class Manufacturer {
    @Id
    private String id;

    private String manufacturerName;

    private String contactPerson;

    private String phone;

    private String email;

    private String address;

    private String paymentTerms;

    private double totalPurchaseAmount;
}
