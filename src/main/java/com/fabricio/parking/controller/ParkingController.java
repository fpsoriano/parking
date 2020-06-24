package com.fabricio.parking.controller;

import com.fabricio.parking.repository.model.parking.ParkingModel;
import com.fabricio.parking.service.ParkingService;
import com.fabricio.parking.vo.customer.CustomerVo;
import com.fabricio.parking.vo.parking.ParkingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = {"Parking API"})
public class ParkingController {

  private final ParkingService parkingService;
  public ParkingController(ParkingService parkingService) {this.parkingService = parkingService;}

  @PostMapping("/parking")
  public ResponseEntity addParking(@ApiParam(value = "Parking object", required = true) @Valid @RequestBody final ParkingVo parkingRequest) {
    log.info("Request : {}", parkingRequest);
    ParkingVo parkingVo = parkingService.save(parkingRequest.toParkingModel()).toParkingVo();
    return new ResponseEntity<>(parkingVo, HttpStatus.CREATED);
  }

  @ApiOperation(
      value = "Get parking by id",
      notes = "Retrieves parking by id to check all the information")
  @GetMapping("/parking/{parkingId}")
  public ParkingVo getOrderById(@ApiParam(value = "Parking id", required = true) @PathVariable final String customerId) {
    return parkingService.findById(customerId).toParkingVo();
  }

  @ApiOperation(
      value = "All parking slots",
      notes = "Retrieves parking slots available in the application")
  @GetMapping("/parking")
  public List<ParkingModel> getAllParking() {
    return parkingService.findAll();
  }
}
