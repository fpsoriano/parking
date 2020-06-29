package com.fabricio.parking.vo.order;

import static com.fabricio.parking.helpers.OrderHelper.mockOrderVo;
import static com.fabricio.parking.helpers.ParkingHelper.mockParkingVo;
import static org.junit.jupiter.api.Assertions.*;

import com.fabricio.parking.helper.ValidationConstants;
import com.fabricio.parking.helpers.BaseTest;
import com.fabricio.parking.vo.parking.ParkingVo;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderVoTest extends BaseTest {

  @Test
  public void validateRequiredFieldParkingId() {
    OrderVo orderVo = mockOrderVo();
    orderVo.setParkingId(null);
    assertViolation(ValidationConstants.PARKING_ID_IS_REQUIRED, orderVo);
  }

  @Test
  public void validateRequiredFieldCustomerId() {
    OrderVo orderVo = mockOrderVo();
    orderVo.setCustomerId(null);
    assertViolation(ValidationConstants.CUSTOMER_ID_IS_REQUIRED, orderVo);
  }

  @Test
  public void validateRequiredFieldPlateCar() {
    OrderVo orderVo = mockOrderVo();
    orderVo.setPlateCar(null);
    assertViolation(ValidationConstants.PLATE_CAR_IS_REQUIRED, orderVo);
  }


}