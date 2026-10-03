package com.sparktech.finalproject.medicine;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {

    @Id
    private String id;


    private String medicineName;

    private String genericName;

    private String category;

    private String manufacturer;

    private String batchNo;

    @Min(value = 0)
    private int stockQuantity;
    
    @DecimalMin(value = "0.0")
    private double unitPrice;

    private LocalDate expiryDate;


    private String status;

    private String description;



}
