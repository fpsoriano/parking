package com.fabricio.parking.vo.parking;

import static com.fabricio.parking.helpers.ParkingHelper.mockParkingVo;
import static com.fabricio.parking.helpers.ParkingHelper.mockPriceVo;
import static com.fabricio.parking.helpers.ParkingHelper.mockSlot;
import static org.junit.jupiter.api.Assertions.*;

import com.fabricio.parking.helper.ValidationConstants;
import com.fabricio.parking.helpers.BaseTest;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;

class SlotVoTest extends BaseTest {

  private static final Validator validator =
      Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  public void validateRequiredFieldNumberOfSlots() {
    SlotVo slotVo = mockSlot();
    slotVo.setNumberOfSlots(null);
    assertViolation(ValidationConstants.NUMBER_OF_SLOTS_IS_REQUIRED, slotVo);
  }

  @Test
  public void validateRequiredFieldNumberOfAvailableSlots() {
    SlotVo slotVo = mockSlot();
    slotVo.setNumberOfAvailableSlots(null);
    assertViolation(ValidationConstants.NUMBER_OF_AVAILABLE_SLOTS_IS_REQUIRED, slotVo);
  }

  @Test
  public void validateTypeField() {
    SlotVo slotVo = mockSlot();
    slotVo.setType(null);
    final Set<ConstraintViolation<Object>> violations = validator.validate(slotVo);
    assertEquals(2, violations.size());
  }

}