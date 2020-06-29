package com.fabricio.parking.vo.parking;

import static com.fabricio.parking.helper.ValidationConstants.NUMBER_OF_AVAILABLE_SLOTS_IS_REQUIRED;
import static com.fabricio.parking.helper.ValidationConstants.NUMBER_OF_SLOTS_IS_REQUIRED;

import com.fabricio.parking.repository.model.parking.SlotModel;
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
@AllArgsConstructor
@NoArgsConstructor
public class SlotVo {

  @NotBlank(message = "Slot type is required")
  @Type(message = "Slot type not valid")
  @ApiModelProperty(example = "SEDAN_CARS", notes = "Valid values:\n"
      + "SEDAN_CARS (sedan cars - gasoline-powered)\n"
      + "POWER_SUPPLY_20W (20kw power supply for electric cars)\n "
      + "POWER_SUPPLY_50W (50kw power supply for electric cars)")
  private String type;

  @ApiModelProperty(example = "10", notes = "Number of slots in the parking")
  @NotNull(message = NUMBER_OF_SLOTS_IS_REQUIRED)
  private Integer numberOfSlots;

  @ApiModelProperty(example = "5", notes = "Number of available slots. It means if the slot is free to park the car")
  @NotNull(message = NUMBER_OF_AVAILABLE_SLOTS_IS_REQUIRED)
  private Integer numberOfAvailableSlots;

  public SlotModel toParkingModel(){
    return SlotModel.builder().type(this.type.toUpperCase()).numberOfSlots(this.numberOfSlots).numberOfAvailableSlots(this.numberOfAvailableSlots).build();
  }

}
