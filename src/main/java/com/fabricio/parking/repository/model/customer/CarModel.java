package com.fabricio.parking.repository.model.customer;

import com.fabricio.parking.vo.customer.CarVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {

  private String model;
  private String plate;
  private String type;

  public CarVo toCarVo(){
    return CarVo.builder().model(this.model).plate(this.plate).type(this.type.toUpperCase()).build();
  }

}
