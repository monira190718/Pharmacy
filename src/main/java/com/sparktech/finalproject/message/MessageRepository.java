package com.sparktech.finalproject.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {

    List<Message>
    findByCustomerEmailOrderByIdAsc(String email);



    List<Message>
    findBySender(String sender);

    @Query("{ 'sender':'CUSTOMER' }")
    List<Message> findCustomerChats();

}
