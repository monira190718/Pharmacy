package com.sparktech.finalproject.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerEmail(String email);

    Order findTopByCustomerEmailOrderByIdDesc(String email);

    List<Order> findByCustomerEmailOrderByIdDesc(String email);
}
