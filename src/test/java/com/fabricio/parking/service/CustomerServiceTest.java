package com.fabricio.parking.service;

import static com.fabricio.parking.helpers.CustomerHelper.mockCustomerVo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabricio.parking.exceptions.NotFoundException;
import com.fabricio.parking.repository.CustomerRepository;
import com.fabricio.parking.repository.model.customer.CustomerModel;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  public static final String CUSTOMER_ID = "customerId";

  @InjectMocks
  private CustomerService customerService;

  @Mock
  private CustomerRepository customerRepository;

  @Test
  public void addCustomer() {
    CustomerModel customerModelExpected = mockCustomerVo().toCustomerModel();
    when(customerRepository.save(any(CustomerModel.class))).thenReturn(mockCustomerVo().toCustomerModel());
    CustomerModel customerModel = customerService.save(customerModelExpected);
    verify(customerRepository, times(1)).save(any(CustomerModel.class));
    assertCustomerFields(customerModelExpected, customerModel);
  }

  @Test
  public void getCustomerById() {
    CustomerModel customerModelExpected = mockCustomerVo().toCustomerModel();
    when(customerRepository.findById(any(String.class))).thenReturn(Optional.of(mockCustomerVo().toCustomerModel()));
    CustomerModel customerModel = customerService.findById(CUSTOMER_ID);
    assertCustomerFields(customerModelExpected, customerModel);
  }

  @Test
  public void getCustomerByIdNotFound() {
    when(customerRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
    assertThrows(NotFoundException.class, () -> {
      customerService.findById(CUSTOMER_ID);
    });
  }

  @Test
  public void findAllCustomer() {
    CustomerModel customerModelExpected = mockCustomerVo().toCustomerModel();
    when(customerRepository.findAll()).thenReturn(Arrays.asList(mockCustomerVo().toCustomerModel()));
    List<CustomerModel> customerModels = customerService.findAll();
    verify(customerRepository, times(1)).findAll();
    Assert.assertEquals(1, customerModels.size());
    assertCustomerFields(customerModelExpected, customerModels.get(0));
  }

  public void assertCustomerFields(CustomerModel customerModelExpected, CustomerModel customerModelVoResponse){
    Assert.assertEquals(customerModelExpected.getId(), customerModelVoResponse.getId());
    Assert.assertEquals(customerModelExpected.getCellphone(), customerModelVoResponse.getCellphone());
    Assert.assertEquals(customerModelExpected.getName(), customerModelVoResponse.getName());
    Assert.assertEquals(customerModelExpected.getCars().get(0).getModel(), customerModelVoResponse.getCars().get(0).getModel());
    Assert.assertEquals(customerModelExpected.getCars().get(0).getPlate(), customerModelVoResponse.getCars().get(0).getPlate());
    Assert.assertEquals(customerModelExpected.getCars().get(0).getType(), customerModelVoResponse.getCars().get(0).getType());
  }

}