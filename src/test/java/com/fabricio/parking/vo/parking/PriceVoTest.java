package com.fabricio.parking.vo.parking;

import static com.fabricio.parking.helpers.ParkingHelper.mockPriceVo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;

class PriceVoTest {

  private static final Validator validator =
      Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  public void validatePriceField() {
    PriceVo priceVo = mockPriceVo();
    priceVo.setPricingPoliciy(null);
    final Set<ConstraintViolation<Object>> violations = validator.validate(priceVo);
    assertEquals(2, violations.size());
  }


}