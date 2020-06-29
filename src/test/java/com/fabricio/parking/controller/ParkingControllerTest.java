package com.fabricio.parking.controller;

import static com.fabricio.parking.helpers.CustomerHelper.mockCustomerVo;
import static com.fabricio.parking.helpers.ParkingHelper.mockParkingVo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.repository.model.parking.ParkingModel;
import com.fabricio.parking.service.ParkingService;
import com.fabricio.parking.vo.customer.CustomerVo;
import com.fabricio.parking.vo.parking.ParkingVo;
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
class ParkingControllerTest {

  @InjectMocks
  private ParkingController parkingController;

  @Mock
  private ParkingService parkingService;

  @Test
  public void saveParking() {
    ParkingVo parkingVoExpected = mockParkingVo();
    when(parkingService.save(any(ParkingModel.class))).thenReturn(mockParkingVo().toParkingModel());
    ResponseEntity<ParkingVo> customerVoResponseEntity = parkingController.addParking(mockParkingVo());
    verify(parkingService, times(1)).save(any(ParkingModel.class));
    assertParkingFields(parkingVoExpected, customerVoResponseEntity.getBody());
  }

  @Test
  public void getParkingById() {
    ParkingVo parkingVoExpected = mockParkingVo();
    when(parkingService.findById(any(String.class))).thenReturn(mockParkingVo().toParkingModel());
    ParkingVo parkingVo = parkingController.getParkingById(parkingVoExpected.getId());
    verify(parkingService, times(1)).findById(any(String.class));
    assertParkingFields(parkingVoExpected, parkingVo);
  }

  @Test
  public void findAllParking() {
    ParkingVo parkingVoExpected = mockParkingVo();
    when(parkingService.findAll()).thenReturn(Arrays.asList(mockParkingVo().toParkingModel()));
    List<ParkingModel> allParking = parkingController.getAllParking();
    verify(parkingService, times(1)).findAll();
    Assert.assertEquals(1, allParking.size());
    assertParkingFields(parkingVoExpected, allParking.get(0).toParkingVo());
  }

  public void assertParkingFields(ParkingVo parkingVoExpected, ParkingVo parkingVoResponse){
    Assert.assertEquals(parkingVoExpected.getId(), parkingVoResponse.getId());
    Assert.assertEquals(parkingVoExpected.getAddress(), parkingVoResponse.getAddress());
    Assert.assertEquals(parkingVoExpected.getName(), parkingVoResponse.getName());
    Assert.assertEquals(parkingVoExpected.getPriceDetails().getPricingPoliciy(), parkingVoResponse.getPriceDetails().getPricingPoliciy());
    Assert.assertEquals(parkingVoExpected.getPriceDetails().getFixedAmount(), parkingVoResponse.getPriceDetails().getFixedAmount());
    Assert.assertEquals(parkingVoExpected.getPriceDetails().getHourPrice(), parkingVoResponse.getPriceDetails().getHourPrice());
    Assert.assertEquals(parkingVoExpected.getSlots().get(0).getNumberOfSlots(), parkingVoResponse.getSlots().get(0).getNumberOfSlots());
    Assert.assertEquals(parkingVoExpected.getSlots().get(0).getType(), parkingVoResponse.getSlots().get(0).getType());
  }

}