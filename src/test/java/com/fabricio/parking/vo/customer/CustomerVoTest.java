package com.fabricio.parking.vo.customer;

import static com.fabricio.parking.helpers.CustomerHelper.mockCustomerVo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fabricio.parking.helper.ValidationConstants;
import com.fabricio.parking.helpers.BaseTest;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;

class CustomerVoTest extends BaseTest {

  @Test
  public void validateRequiredFieldName() {
    CustomerVo customerVo = mockCustomerVo();
    customerVo.setName(null);
    assertViolation(ValidationConstants.NAME_IS_REQUIRED, customerVo);
  }

  @Test
  public void validateRequiredFieldCellphone() {
    CustomerVo customerVo = mockCustomerVo();
    customerVo.setCellphone(null);
    assertViolation(ValidationConstants.CELLPHONE_IS_REQUIRED, customerVo);
  }

  @Test
  public void validateRequiredFieldId() {
    CustomerVo customerVo = mockCustomerVo();
    customerVo.setId(null);
    assertViolation(ValidationConstants.ID_IS_REQUIRED, customerVo);
  }



}