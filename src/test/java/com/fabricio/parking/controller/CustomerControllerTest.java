package com.fabricio.parking.controller;

import static com.fabricio.parking.helpers.CustomerHelper.mockCustomerVo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.service.CustomerService;
import com.fabricio.parking.vo.customer.CustomerVo;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  @InjectMocks
  private CustomerController customerController;

  @Mock
  private CustomerService customerService;

  @Test
  public void addCustomer() {
    CustomerVo customerVoRequest = mockCustomerVo();
    when(customerService.save(any(CustomerModel.class))).thenReturn(mockCustomerVo().toCustomerModel());
    ResponseEntity<CustomerVo> customerVoResponseEntity = customerController.addCustomer(customerVoRequest);
    verify(customerService, times(1)).save(any(CustomerModel.class));
    assertCustomerFields(customerVoRequest, customerVoResponseEntity.getBody());
  }

  @Test
  public void findCustomer() {
    CustomerVo customerVoExpected = mockCustomerVo();
    when(customerService.findById(any(String.class))).thenReturn(mockCustomerVo().toCustomerModel());
    CustomerVo customerVo = customerController.getCustomerById(customerVoExpected.getId());
    verify(customerService, times(1)).findById(customerVoExpected.getId());
    assertCustomerFields(customerVoExpected, customerVo);
  }

  @Test
  public void findAllCustomer() {
    when(customerService.findAll()).thenReturn(Arrays.asList(mockCustomerVo().toCustomerModel()));
    List<CustomerModel> allCustomers = customerController.getAllCustomers();
    verify(customerService, times(1)).findAll();
    Assert.assertEquals(1, allCustomers.size());
  }

  public void assertCustomerFields(CustomerVo customerVoExpected, CustomerVo customerVoResponse){
    Assert.assertEquals(customerVoExpected.getId(), customerVoResponse.getId());
    Assert.assertEquals(customerVoExpected.getCellphone(), customerVoResponse.getCellphone());
    Assert.assertEquals(customerVoExpected.getName(), customerVoResponse.getName());
    Assert.assertEquals(customerVoExpected.getCars().get(0).getModel(), customerVoResponse.getCars().get(0).getModel());
    Assert.assertEquals(customerVoExpected.getCars().get(0).getPlate(), customerVoResponse.getCars().get(0).getPlate());
    Assert.assertEquals(customerVoExpected.getCars().get(0).getType(), customerVoResponse.getCars().get(0).getType());
  }


}