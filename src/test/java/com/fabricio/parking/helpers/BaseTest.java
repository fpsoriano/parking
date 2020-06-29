package com.fabricio.parking.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class BaseTest {

  private static final Validator validator =
      Validation.buildDefaultValidatorFactory().getValidator();


  public static void assertViolation(final String expectedViolation, final Object object) {

    final Set<ConstraintViolation<Object>> violations = validator.validate(object);
    assertEquals(1, violations.size());
    violations.forEach(
        constraintViolation -> {
          assertEquals(expectedViolation, constraintViolation.getMessage());
        });
  }

}
