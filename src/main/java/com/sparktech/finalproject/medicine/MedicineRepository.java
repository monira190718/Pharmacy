package com.sparktech.finalproject.medicine;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MedicineRepository extends MongoRepository<Medicine, String> {

    int countByCategory(String category);

    List<Medicine> findAllByOrderByExpiryDateAsc();

}
