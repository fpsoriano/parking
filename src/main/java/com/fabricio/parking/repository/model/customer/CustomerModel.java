package com.fabricio.parking.repository.model.customer;

import com.fabricio.parking.vo.customer.CarVo;
import com.fabricio.parking.vo.customer.CustomerVo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class CustomerModel {

  @Id
  private String id;
  private String name;
  private String cellphone;
  private List<CarModel> cars;

  public CustomerVo toCustomerVo() {
    return CustomerVo.builder().id(this.id).name(this.name).cellphone(this.cellphone).cars(this.cars.stream().map(
        CarModel::toCarVo).collect(
        Collectors.toList())).build();
  }

}
