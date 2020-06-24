package com.fabricio.parking.vo.parking;

import com.fabricio.parking.repository.model.parking.SlotModel;
import com.fabricio.parking.vo.parking.validator.Type;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotVo {

  @NotBlank(message = "Slot type is required")
  @Type(message = "Slot type not valid")
  @ApiModelProperty(example = "SEDAN_CARS", notes = "Valid values:\n"
      + "SEDAN_CARS (sedan cars - gasoline-powered)\n"
      + "POWER_SUPPLY_20W (20kw power supply for electric cars)\n "
      + "POWER_SUPPLY_50W (50kw power supply for electric cars)")
  private String type;

  @ApiModelProperty(example = "10", notes = "Number of slots in the parking")
  @NotNull(message = "Number of slots  is required")
  private Integer numberOfSlots;

  @ApiModelProperty(example = "5", notes = "Number of available slots. It means if the slot is free to park the car")
  @NotNull(message = "Number of available is required")
  private Integer numberOfAvailableSlots;

  public SlotModel toParkingModel(){
    return SlotModel.builder().type(this.type.toUpperCase()).numberOfSlots(this.numberOfSlots).numberOfAvailableSlots(this.numberOfAvailableSlots).build();
  }

}
