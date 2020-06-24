package com.fabricio.parking.vo.parking.validator;

import com.fabricio.parking.vo.parking.PricingPolicyEnum;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PricingPolicyValidator implements ConstraintValidator<PricingPolicy, String> {

  @Override
  public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
    try {
      PricingPolicyEnum.valueOf(str);
      return true;
    } catch (IllegalArgumentException|NullPointerException e) {
      return false;
    }
  }

}