package com.fabricio.parking.service;

import static com.fabricio.parking.helpers.ParkingHelper.mockParkingVo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fabricio.parking.exceptions.NotFoundException;
import com.fabricio.parking.repository.ParkingRepository;
import com.fabricio.parking.repository.model.parking.ParkingModel;
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
class ParkingServiceTest {

  public static final String PARKING_ID = "parkingId";

  @InjectMocks
  private ParkingService parkingService;

  @Mock
  private ParkingRepository parkingRepository;

  @Test
  public void addParking() {
    ParkingModel parkingModelExpected = mockParkingVo().toParkingModel();
    when(parkingRepository.save(any(ParkingModel.class))).thenReturn(mockParkingVo().toParkingModel());
    ParkingModel parkingModel = parkingService.save(mockParkingVo().toParkingModel());
    verify(parkingRepository, times(1)).save(any(ParkingModel.class));
    assertParkingFields(parkingModelExpected, parkingModel);
  }

  @Test
  public void getParkingById() {
    ParkingModel parkingModelExpected = mockParkingVo().toParkingModel();
    when(parkingRepository.findById(any(String.class))).thenReturn(Optional.of(mockParkingVo().toParkingModel()));
    ParkingModel parkingModel = parkingService.findById(PARKING_ID);
    assertParkingFields(parkingModelExpected, parkingModel);
  }

  @Test
  public void getParkingByIdNotFound() {
    when(parkingRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(null));
    assertThrows(NotFoundException.class, () -> {
      parkingService.findById(PARKING_ID);
    });
  }

  @Test
  public void findAllParking() {
    ParkingModel parkingModelExpected = mockParkingVo().toParkingModel();
    when(parkingRepository.findAll()).thenReturn(Arrays.asList(mockParkingVo().toParkingModel()));
    List<ParkingModel> allParking = parkingService.findAll();
    verify(parkingRepository, times(1)).findAll();
    Assert.assertEquals(1, allParking.size());
    assertParkingFields(parkingModelExpected, allParking.get(0));
  }

  public void assertParkingFields(ParkingModel parkingModelExpected, ParkingModel parkingModelResponse){
    Assert.assertEquals(parkingModelExpected.getId(), parkingModelResponse.getId());
    Assert.assertEquals(parkingModelExpected.getAddress(), parkingModelResponse.getAddress());
    Assert.assertEquals(parkingModelExpected.getName(), parkingModelResponse.getName());
    Assert.assertEquals(parkingModelExpected.getPriceDetails().getPricingPoliciy(), parkingModelResponse.getPriceDetails().getPricingPoliciy());
    Assert.assertEquals(parkingModelExpected.getPriceDetails().getFixedAmount(), parkingModelResponse.getPriceDetails().getFixedAmount());
    Assert.assertEquals(parkingModelExpected.getPriceDetails().getHourPrice(), parkingModelResponse.getPriceDetails().getHourPrice());
    Assert.assertEquals(parkingModelExpected.getSlots().get(0).getNumberOfSlots(), parkingModelResponse.getSlots().get(0).getNumberOfSlots());
    Assert.assertEquals(parkingModelExpected.getSlots().get(0).getType(), parkingModelResponse.getSlots().get(0).getType());
  }

}