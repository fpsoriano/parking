package com.fabricio.parking.vo.customer;

import static com.fabricio.parking.helpers.CustomerHelper.mockCarVo;
import static org.junit.jupiter.api.Assertions.*;

import com.fabricio.parking.helper.ValidationConstants;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;

class CarVoTest {

  private static final Validator validator =
      Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  public void validateRequiredFieldType() {
    CarVo carVo = mockCarVo();
    carVo.setType(null);
    final Set<ConstraintViolation<Object>> violations = validator.validate(carVo);
    assertEquals(2, violations.size());
  }

  @Test
  public void validateRequiredFieldPlate() {
    CarVo carVo = mockCarVo();
    carVo.setPlate(null);
    assertViolation(ValidationConstants.PLATE_IS_REQUIRED, carVo);
  }

  @Test
  public void validateRequiredFieldModel() {
    CarVo carVo = mockCarVo();
    carVo.setModel(null);
    assertViolation(ValidationConstants.CAR_MODEL_IS_REQUIRED, carVo);
  }

  public static void assertViolation(final String expectedViolation, final Object object) {

    final Set<ConstraintViolation<Object>> violations = validator.validate(object);
    assertEquals(1, violations.size());
    violations.forEach(
        constraintViolation -> {
          assertEquals(expectedViolation, constraintViolation.getMessage());
        });
  }

}