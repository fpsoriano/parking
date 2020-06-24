package com.fabricio.parking.vo.customer;

import com.fabricio.parking.repository.model.customer.CarModel;
import com.fabricio.parking.vo.parking.validator.Type;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarVo {

  @ApiModelProperty(example = "Golf 2.0 2020", notes = "Car model")
  @NotNull(message = "Number of slots  is required")
  private String model;

  @ApiModelProperty(example = "GMM-4755", notes = "Car plate")
  @NotNull(message = "plate is required")
  private String plate;

  @NotBlank(message = "Car type is required")
  @Type(message = "Car type not valid")
  @ApiModelProperty(example = "SEDAN_CARS", notes = "Valid values:\n"
      + "SEDAN_CARS (sedan cars - gasoline-powered)\n"
      + "POWER_SUPPLY_20W (20kw power supply for electric cars)\n "
      + "POWER_SUPPLY_50W (50kw power supply for electric cars)")
  private String type;

  public CarModel toCarModel(){
    return CarModel.builder().model(this.model).plate(this.plate).type(this.type.toUpperCase()).build();
  }

}
