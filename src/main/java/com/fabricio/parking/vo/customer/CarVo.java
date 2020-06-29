package com.fabricio.parking.vo.customer;

import static com.fabricio.parking.helper.ValidationConstants.CAR_MODEL_IS_REQUIRED;
import static com.fabricio.parking.helper.ValidationConstants.CAR_TYPE_IS_REQUIRED;
import static com.fabricio.parking.helper.ValidationConstants.PLATE_IS_REQUIRED;

import com.fabricio.parking.repository.model.customer.CarModel;
import com.fabricio.parking.vo.parking.validator.Type;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarVo {

  @ApiModelProperty(example = "Golf 2.0 2020", notes = "Car model")
  @NotNull(message = CAR_MODEL_IS_REQUIRED)
  private String model;

  @ApiModelProperty(example = "GMM-4755", notes = "Car plate")
  @NotNull(message = PLATE_IS_REQUIRED)
  private String plate;

  @NotBlank(message = CAR_TYPE_IS_REQUIRED)
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
