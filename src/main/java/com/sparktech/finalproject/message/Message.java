package com.sparktech.finalproject.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class Message {

    @Id
    private String id;


    private String customerName;


    private String customerEmail;


    private String sender;
    // CUSTOMER / ADMIN


    private String message;


    private String date;
    
}
