package com.fabricio.parking.repository;


import com.fabricio.parking.repository.model.order.OrderModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderRepository extends MongoRepository<OrderModel, String> {
}


