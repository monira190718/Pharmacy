package com.sparktech.finalproject.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="reviews")
public class Review {
    @Id
    private String id;


    private String customerName;


    private String customerEmail;

    @NotNull(message="Rating required")
    private int rating;

    @NotBlank(message="Feedback required")
    private String comment;


    private String date;

}
