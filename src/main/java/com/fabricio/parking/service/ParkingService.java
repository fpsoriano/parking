package com.fabricio.parking.service;

import com.fabricio.parking.exceptions.IssueEnum;
import com.fabricio.parking.exceptions.NotFoundException;
import com.fabricio.parking.repository.ParkingRepository;
import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.repository.model.parking.ParkingModel;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

  private final ParkingRepository parkingRepository;

  public ParkingService(ParkingRepository parkingRepository) {this.parkingRepository = parkingRepository;}

  public ParkingModel save(ParkingModel parkingModel) {
    return parkingRepository.save(parkingModel);
  }

  public List<ParkingModel> findAll() {
    return parkingRepository.findAll();
  }

  public ParkingModel findById(String idParking) {
    Optional<ParkingModel> customerModel = parkingRepository.findById(idParking);
    if (!customerModel.isPresent()) {
      throw NotFoundException.notFound(IssueEnum.PARKING_NOT_FOUND, idParking);
    }
    return customerModel.get();
  }
}