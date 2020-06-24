package com.fabricio.parking.service;

import com.fabricio.parking.exceptions.IssueEnum;
import com.fabricio.parking.exceptions.NotFoundException;
import com.fabricio.parking.repository.CustomerRepository;
import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.vo.customer.CustomerVo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

  public CustomerModel save(CustomerModel customerModel) {
    return customerRepository.save(customerModel);
  }

  public List<CustomerModel> findAll() {
    return customerRepository.findAll();
  }

  public CustomerModel findById(String idCustomer) {
    Optional<CustomerModel> customerModel = customerRepository.findById(idCustomer);
    if (!customerModel.isPresent()) {
      throw NotFoundException.notFound(IssueEnum.CUSTOMER_NOT_FOUND, idCustomer);
    }
    return customerModel.get();
  }

}