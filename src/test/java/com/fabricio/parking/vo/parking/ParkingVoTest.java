package com.fabricio.parking.vo.parking;

import static com.fabricio.parking.helpers.ParkingHelper.mockParkingVo;

import com.fabricio.parking.helper.ValidationConstants;
import com.fabricio.parking.helpers.BaseTest;
import org.junit.jupiter.api.Test;

class ParkingVoTest extends BaseTest {


  @Test
  public void validateRequiredFieldName() {
    ParkingVo parkingVo = mockParkingVo();
    parkingVo.setName(null);
    assertViolation(ValidationConstants.NAME_IS_REQUIRED, parkingVo);
  }

  @Test
  public void validateRequiredFieldPrice() {
    ParkingVo parkingVo = mockParkingVo();
    parkingVo.setPriceDetails(null);
    assertViolation(ValidationConstants.PRICE_DETAILS_IS_REQUIRED, parkingVo);
  }

}