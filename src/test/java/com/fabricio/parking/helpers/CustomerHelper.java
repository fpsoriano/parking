package com.fabricio.parking.helpers;

import com.fabricio.parking.repository.model.customer.CarModel;
import com.fabricio.parking.repository.model.customer.CustomerModel;
import com.fabricio.parking.vo.customer.CarVo;
import com.fabricio.parking.vo.customer.CustomerVo;
import java.util.Arrays;

public class CustomerHelper {

  public static CustomerVo mockCustomerVo() {
    CustomerVo customerVo = new CustomerVo();
    customerVo.setId("customerId");
    customerVo.setCellphone("9367455");
    customerVo.setName("Fabricio Test");

    CarVo carVo = mockCarVo();
    customerVo.setCars(Arrays.asList(carVo));
    return customerVo;
  }

  public static CarVo mockCarVo() {
    CarVo carVo = new CarVo();
    carVo.setModel("Corolla");
    carVo.setPlate("TMM-3434");
    carVo.setType("SEDAN_CARS");
    return carVo;
  }

}
