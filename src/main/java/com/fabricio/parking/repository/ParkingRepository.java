package com.fabricio.parking.repository;

import com.fabricio.parking.repository.model.parking.ParkingModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ParkingRepository extends MongoRepository<ParkingModel,String> {
}


