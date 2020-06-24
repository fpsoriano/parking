package com.fabricio.parking.vo.parking;

import com.fabricio.parking.repository.model.parking.ParkingModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingVo {

  @ApiModelProperty(example = "P01", notes = "The Parking Id. If not set, will be generate by the application")
  private String id;

  @NotBlank(message = "Name is required")
  @ApiModelProperty(example = "Parking Name Test", notes = "The Parking Name")
  private String name;

  @ApiModelProperty(example = "Dean St", notes = "The Parking Name")
  private String address;
  
  @NotNull
  private PriceVo priceDetails;

  @Valid
  private List<SlotVo> slots;

  public ParkingModel toParkingModel() {
    return ParkingModel.builder()
        .id(this.id)
        .name(this.name)
        .address(this.address)
        .priceDetails(this.getPriceDetails().toPriceModel())
        .slots(this.slots.stream().map(SlotVo::toParkingModel).collect(Collectors.toList())).build();
  }

}
