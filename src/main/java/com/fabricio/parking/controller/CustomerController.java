package com.fabricio.parking.controller;

import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.service.CustomerService;
import com.fabricio.parking.vo.customer.CustomerVo;
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
@Api(tags = {"Customer API"})
public class CustomerController {

  private final CustomerService customerService;
  public CustomerController(CustomerService customerService) {this.customerService = customerService;}

  @ApiOperation(
      value = "Register customer",
      notes = "Register customer in the application")
  @PostMapping("/customer")
  public ResponseEntity addCustomer(@ApiParam(value = "Customer object", required = true) @Valid @RequestBody final CustomerVo customerRequest) {
    log.info("Request : {}", customerRequest);
    CustomerVo customerVo = customerService.save(customerRequest.toCustomerModel()).toCustomerVo();
    return new ResponseEntity<>(customerVo, HttpStatus.CREATED);
  }

  @ApiOperation(
      value = "Get customer by id",
      notes = "Retrieves customer by id to check all the information")
  @GetMapping("/customer/{customerId}")
  public CustomerVo getCustomerById(@ApiParam(value = "Customer id", required = true) @PathVariable final String customerId) {
    return customerService.findById(customerId).toCustomerVo();
  }

  @ApiOperation(
      value = "Retrieves all customers",
      notes = "Retrieves customers registered in the application")
  @GetMapping("/customer")
  public List<CustomerModel> getAllCustomers() {
    return customerService.findAll();
  }
}
