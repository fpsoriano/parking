package com.fabricio.parking.vo.parking.validator;

import com.fabricio.parking.vo.parking.TypeEnum;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TypeValidator implements ConstraintValidator<Type, String> {

  @Override
  public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
    try {
      TypeEnum.valueOf(str);
      return true;
    } catch (IllegalArgumentException|NullPointerException e) {
      return false;
    }
  }

}
