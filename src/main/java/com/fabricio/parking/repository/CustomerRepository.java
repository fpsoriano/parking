package com.fabricio.parking.repository;


import com.fabricio.parking.repository.model.customer.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface CustomerRepository extends MongoRepository<CustomerModel, String> {
}


